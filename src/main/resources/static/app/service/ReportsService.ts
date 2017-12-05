import {Report} from "../vo/report";

export class ReportsService {

    public static $inject = ["$http"];

    constructor(private $http: ng.IHttpService) {

    }

    public getReports() {
        return this.$http({
            url: "/reports/all",
            method: "GET"
        })
    }

    public getReportById(reportId:number) {
        return this.$http({
            url:"/reports/"+reportId,
            method:"GET"
        });
    }

    public generateReport(reportId:number) {
        return this.$http({
            url: "/reports/generate/"+reportId,
            method: "POST",
        })
    }

    public getCSVGenerate(reports:Report[]) {
        let i: number;
        let ids = [] as number[];
        for (i = 0; i < reports.length; i++) {
            ids[i] = reports[i].id;
        }
        console.log("Ids: " + (ids as number[]));
        return this.$http({
            url: "/reports/generated",
            method: "POST",
            data: ids,
            headers: {
                "Content-Type": "application/json"
            }
        })
    }

    public isCSVGenerated(id:number) {
        return this.$http({
            url: "/reports/generated/"+id,
            method: "GET"
        });
    }

}