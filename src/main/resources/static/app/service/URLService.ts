import {Parameters} from "../vo/parameters";
import {Site} from "../vo/site";
import {Channel} from "../vo/channel";

export class URLService {

    public static $inject = ["$location"]

     constructor(private $location: ng.ILocationService) {

     }

     public getReportId() {
        return this.getQueryParameter('reportId');
     }

     private getQueryParameter(parameterName:string) {
         return this.$location.search()[parameterName];
     }

     public getParamsFromUrl() : Parameters {
         let siteId = this.$location.search()['siteId'];
         let channelId = this.$location.search()['channelId'];
         let from = this.$location.search()['from'];
         let to = this.$location.search()['to'];
         let params = new Parameters();
         params.site = new Site();
         params.site.id = siteId;
         params.channel = new Channel();
         params.channel.id = channelId;
         params.from = from;
         params.to = to;
         return params;
     }
}