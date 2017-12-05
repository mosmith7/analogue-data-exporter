import {ParametersService} from "../service/ParametersService";
import {ReportsService} from "../service/ReportsService";
import {Parameters} from "../vo/parameters";
import {Report} from "../vo/report";
import {URLService} from "../service/URLService";
import {Site} from "../vo/site";
import {IWindowService} from "angular";

export class ReportController {

    public static $inject = ["parametersService", "reportsService", "urlService", "$window"];

    public reportId:number;
    public siteId:number;
    public channelId:number;
    public from:string;
    public to:string;
    public report:Report;

    constructor(private parametersService:ParametersService, private reportsService:ReportsService,
                private urlService:URLService, private $window:IWindowService) {
        // If URL has reportId then override any other parameters in the parameters service
        this.parseURL();
    }

    public generateReport() {
        console.log("generateReport("+this.reportId+")");
        this.report.csvGenerating = true;
        this.report.csvGenerated = false;
        this.reportsService.generateReport(this.reportId).then((r) => {
            this.report.csvGenerated = true;
            this.report.csvGenerating = false;
        }, (e) => {
            alert("Epic Fail!" + e)
        })
    }

    public editParameters() {
        console.log("++editParameters()");
        this.parametersService.setSavedParams(this.reportId, this.siteId, this.channelId, this.from, this.to);
        let url = "/#!/reports";
        console.log("Adding parameters to url");
        url += "?siteId="+this.siteId;
        url += "&channelId="+this.channelId;
        url += "&from="+this.from;
        url += "&to="+this.to;
        this.$window.location.href = url;
    }

    private parseURL() {
        let reportId = this.urlService.getReportId();
        console.log("Site from URL: " + reportId);
        if (reportId!=null) {
            this.reportId = reportId;
            console.log("reportId from URL set");
            // Get the parameters from the ParametersService if present
            let params = this.parametersService.getSavedParams();
            // Otherwise get the parameters from the database
            if (params==null) {
                this.parametersService.getParameters(reportId).then((r) => {
                    params = r.data as Parameters;
                    this.setFromParams(params);
                }, (e) => {
                    console.log("Error: " + e.status + e.statusMessage);
                });
            } else {
                console.log("Using saved parameters from parametersService");
                this.setFromParams(params);
            }
        }
    }

    private setFromParams(params:Parameters) {
        this.siteId = params.site.id;
        this.channelId = params.channel.id;
        this.from = new Date(params.from).toISOString();
        this.to = new Date(params.to).toISOString();
        this.reportId = params.id;
        this.report = new Report();
        this.report.id = this.reportId;
        this.report.csvGenerated = false;
        this.report.csvGenerating = false;
        this.report.site = params.site;
        this.report.channel = params.channel;
        this.reportsService.isCSVGenerated(this.reportId).then((r)=> {
            this.report.csvGenerated = r.data as boolean;
        }, (e) => {
            console.log("Error: " + e.status + e.statusMessage);
        })

    }
}