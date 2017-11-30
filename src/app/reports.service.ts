import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Report} from './report';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {of} from "rxjs/observable/of";
import {catchError} from "rxjs/operators";
import {pipe} from "rxjs/Rx";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ReportsService {

  constructor(
    private http: HttpClient) { }

  // private url = 'http://localhost:8090/reports';  // URL to web api

  private reportsUrl = 'api/reports';  // URL to web api

  /** GET reports from the server */
  getReports (): Observable<Report[]> {
    console.log('Code is about to retrieve reports')
    return this.http.get<Report[]>(this.reportsUrl)
    .pipe(
      catchError(this.handleError<Report[]>('getReports', []))
    );
  }

  /** GET report by id. Will 404 if id not found */
  getReport(id: number): Observable<Report> {
    const url = `${this.reportsUrl}/${id}`;
    return this.http.get<Report>(url).pipe(
      catchError(this.handleError<Report>(`getReport id=${id}`))
    );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
