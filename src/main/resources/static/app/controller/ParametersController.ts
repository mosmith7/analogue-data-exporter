import {Channel} from "../vo/channel";
import {Site} from "../vo/site";
import {Report} from "../vo/report";
import {ParametersService} from "../service/ParametersService";
import {ReportsService} from "../service/ReportsService";
import {IWindowService} from "angular";
import {URLService} from "../service/URLService";
import {Parameters} from "../vo/parameters";


export class ParametersController {

    public static $inject = ["$window", "parametersService", "reportsService", "urlService"];

    public sites:Site[];
    public siteId:number;
    public channels:Channel[];
    public channelId:number;
    public from:Date;
    public to:Date;
    public reports:Report[];
    public csvGenerated: boolean[];
    public reportId:number;

    constructor(private $window:IWindowService, private parametersService:ParametersService,
                private reportsService:ReportsService, private urlService:URLService) {

        this.setParametersFromUrl()

        // Get the site data
        parametersService.getSites().then( (r) => {
            this.sites = r.data as Site[]
        }, (e) => {
            console.log("Error: " + e)
        });

        // Don't allow the channel data to be picked until site is picked

        // Assign values if they are provided via the URL

        // Get the reports data
        reportsService.getReports().then( (r) => {
            this.reports = r.data as Report[];
            this.populateCSVGenerated();
            console.log("ParametersController found reports: " + this.reports);
        }, (e) => {
            console.log("Error: " + e);
        })
    }

    private setParametersFromUrl() {
        console.log("Setting parameters from URL");
        let params = this.urlService.getParamsFromUrl(); 
        if (params!=null) {
            this.siteId = params.site.id;
            if (params.site.id!=null) {
                // load channels for site before assigning the specific channel
                this.parametersService.getChannelsForSite(this.siteId).then( (r) => {
                    this.channels = r.data as Channel[]
                    this.channelId = params.channel.id;
                }, function error(e) {
                    console.log("Error: " + e)
                })
                this.from = new Date(params.from);
                this.to = new Date(params.to);
            }
        }
    }

    public viewReport(id:number) {
        console.log("redirecting to specific page for report: " + id);
        // Get parameters for specific report and save them in parametersService so next page can pick them up
        this.reportsService.getReportById(id).then( (r) => {
            let params = r.data as Parameters;
            this.parametersService.setSavedParams(id, params.site.id, params.channel.id, params.from, params.to);
            this.$window.location.href = "#!/report?reportId=" + id;
        }, (e) => {
            console.log("Error: " + e.status + e.statusMessage);
        });

    }

    public onSiteSelect() {
        console.log("Selected Site: " + this.siteId);
        // Get the channel data
        this.parametersService.getChannelsForSite(this.siteId).then( (r) => {
            this.channels = r.data as Channel[]
        }, function error(e) {
            console.log("Error: " + e)
        })
    }

    public onChannelSelect() {
        console.log("Selected Channel: " + this.channelId);
    }

    public onDateFromSelect() {
        console.log("Selected date from: " + this.from);
    }

    public onDateToSelect() {
        console.log("Selected date to: " + this.to);
    }

    public submitParameters() {
        console.log("Selected Parameters: " + this.siteId + this.channelId + this.from + this.to);
        if (!this.checkParameters()) return;
        this.parametersService.setSavedParams(this.reportId, this.siteId, this.channelId, this.from.toISOString(), this.to.toISOString());
        return this.parametersService.saveParameters(this.siteId, this.channelId, this.from.toISOString(), this.to.toISOString()).then((r) => {
                this.reportId = r.data as number;
                window.location.href = "#!/report?reportId=" + r.data
            }, (e) => {
                console.log("Error: " + e);
                if (e.status==500) {
                    alert("No data was found for the parameters selected");
                }
            })
    }

    public generateCSV(id:number) {
        console.log("generateCSV("+id+")");
        this.setCSVGenerated(id, false);
        this.setCSVGenerating(id, true);
        this.parametersService.generateCSV(id).then( (r) => {
            console.log("CSV generated for report " + id);
            this.setCSVGenerated(id,true);
            this.setCSVGenerating(id, false);
        }, (e) => {
            console.log("Error generating report: " + e.status + e.statusMessage);
        })
    }

    public getReport(id:number) : Report {
        let index:number = -1;
        if (this.reports!=null) {
            console.log("Reports: " + this.reports.length + "id: " + id);
            for(let i:number = 0; i < this.reports.length; i++) {
                if (this.reports[i].id==id) {
                    index = i;
                }
            }
            if (index!=-1) {
                console.log("Returning report: " + this.reports[index] + " with index: " + index);
                return this.reports[index];
            } else {
                console.log("Error getting report: " + id);
                return null;
            }

        } else {
            console.log("Reports data in service empty");
            return null;
        }
    }

    public saveReport(report:Report) {
        for(let i:number = 0; i < this.reports.length; i++) {
            if (this.reports[i].id===report.id) {
                this.reports[i] = report;
            }
        }
    }

    public setCSVGenerated(id:number, csvGenerated:boolean) {
        console.log("setCSVGenerated("+id+", "+csvGenerated+")");
        let report:Report = this.getReport(id);
        report.csvGenerated=csvGenerated;
        this.saveReport(report);
    }

    public setCSVGenerating(id:number, csvGenerating:boolean) {
        let report:Report = this.getReport(id);
        report.csvGenerating=csvGenerating;
        this.saveReport(report);
    }

    public populateCSVGenerated() {
        if (this.reports!=null) {
            // Get whether the reports data csvs have been generated
            console.log("Reports: " + (this.reports as Report[]));
            this.reportsService.getCSVGenerate(this.reports).then((r) => {
                let csvGenerated = r.data as boolean[];
                for (let i = 0; i < this.reports.length; i++) {
                    this.setCSVGenerated(this.reports[i].id, csvGenerated[i]);
                    this.reports[i].csvGenerated = csvGenerated[i]
                }
                console.log("CSVs generated: " + csvGenerated);
                console.log("Reports with CSV data: " + (this.reports as Report[]));
            }, (e) => {
                console.log("Error: " + e.status + e.statusText);
            })
        } else {
            console.log("Could not populate CSV Generated fields for all reports")
        }
    }

    private checkParameters() : boolean {
        if (this.siteId==null) {
            alert("Please select a site")
            return false;
        } else if (this.channelId==null) {
            alert("Please select a channel")
            return false;
        } else if (!this.isDateValid(this.from)) {
            alert("Please select a date from value")
            return false;
        } else if (!this.isDateValid(this.to)) {
            alert("Please select a date to value")
            return false;
        }
        return true;
    }

    private isDateValid(d:Date) : boolean {
        console.log("is date valid: " + d);
        if ( Object.prototype.toString.call(d) === "[object Date]" ) {
            // it is a date
            if ( isNaN( d.getTime() ) ) {  // d.valueOf() could also work
                // date is not valid
                console.log("Date not valid");
                return false;
            }
            else {
                // date is valid
                console.log("Date valid");
                return true;
            }
        }
        else {
            // not a date
            console.log("Date not valid");
            return false;
        }
    }

}