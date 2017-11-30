import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Report} from './report';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {of} from "rxjs/observable/of";
import {catchError} from "rxjs/operators";

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable()
export class ReportsService {

  constructor(
    private http: HttpClient) { }

  private url = 'http://localhost:8090/reports';  // URL to web api

  /** GET reports from the server */
  getReports (): Observable<Report[]> {
    console.log('Code is about to retrieve reports')
    return this.http.get<Report[]>(this.url + '/all');
    // .pipe(
    //   catchError(this.handleError<Report[]>('getReports', []))
    // );
  }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
