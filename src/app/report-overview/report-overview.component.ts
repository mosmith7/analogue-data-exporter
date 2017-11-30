import {Component, Input, OnInit} from '@angular/core';
import {ReportsService} from '../reports.service';
import {Report} from '../report';

@Component({
  selector: 'app-report-overview',
  templateUrl: './report-overview.component.html',
  styleUrls: ['./report-overview.component.css']
})
export class ReportOverviewComponent implements OnInit {

  reports: Report[];

  constructor(private reportService: ReportsService) { }

  ngOnInit() {
    this.getReports();
  }

  getReports(): void {
    this.reportService.getReports().subscribe(reports => this.reports = reports);
  }

}
