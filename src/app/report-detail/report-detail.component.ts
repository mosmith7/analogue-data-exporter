import {Component, Input, OnInit} from '@angular/core';

import {Report} from '../report';
import {ActivatedRoute} from '@angular/router';

import {ReportsService} from '../reports.service';

@Component({
  selector: 'app-report-detail',
  templateUrl: './report-detail.component.html',
  styleUrls: ['./report-detail.component.css']
})
export class ReportDetailComponent implements OnInit {

  @Input() report: Report;

  constructor(
    private route: ActivatedRoute,
    private reportService: ReportsService
  ) {}

  ngOnInit(): void {
    this.getReport();
  }

  getReport(): void {
    const idRoute = +this.route.snapshot.paramMap.get('id')
    this.reportService.getReport(idRoute).subscribe(report => this.report = report);
  }

}
