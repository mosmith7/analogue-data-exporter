import {ILocationService} from "angular";
import {Parameters} from "../vo/parameters";
import {Site} from "../vo/site";
import {Channel} from "../vo/channel";

export class ParametersService {

    public static $inject = ["$http", "$location"];

    savedParams:Parameters;

    constructor(protected $http: ng.IHttpService, private $location:ILocationService) {

    }

    public setSavedParams(reportId:number, siteId:number, channelId:number, from:string, to:string) {
        let params = new Parameters();
        params.id = reportId;
        params.site = new Site();
        params.site.id = siteId;
        params.channel = new Channel();
        params.channel.id = channelId;
        params.from = from;
        params.to = to;
        this.savedParams = params;
    }

    public getSavedParams() {
        return this.savedParams;
    }

    public getSites() {
        return this.$http({
            url: "/sites",
            method: "GET"
        })
    }

    public getChannelsForSite(siteId:number) {
        return this.$http({
            url: "/channels/site/"+siteId,
            method: "GET"
        })
    }

    public saveParameters(siteId:number, channelId:number, from:string, to:string) {
        return this.$http({
            url: "/reports/parameters",
            method: "POST",
            data: JSON.stringify({
                siteId: siteId,
                channelId: channelId,
                from: from,
                to: to
            })
        });
    }

    public generateCSV(reportId:number) {
        return this.$http({
            url: "/reports/generate/"+reportId,
            method: "POST"
        });
    }

    public getParameters(reportId:number) {
        return this.$http({
            url:"reports/parameters/"+reportId,
            method:"GET"
        });
    }

    // public getSiteIdFromUrl() : number {
    //     console.log("Parsing url");
    //     let searchObject = this.$location.search();
    //     let siteId = this.$location.search("site") as number;
    //     console.log("Site from URL: " + );
    //     return
    // }

}