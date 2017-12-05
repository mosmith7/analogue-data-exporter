import {Channel} from "./channel";
import {Site} from "./site";

export class Report {
    id:number;
    site:Site;
    channel:Channel;
    from:number;
    to:number;
    csvGenerated:boolean = false;
    csvGenerating:boolean = false;
}