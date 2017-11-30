import { InMemoryDbService } from 'angular-in-memory-web-api';

export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const reports = [{"id":1,"site":{"id":771,"name":"SS16/1","code":"SMT1","postcode":""},"channel":{"id":10236422,"name":"T648 C1:IOUT"},"from":973123200000,"to":1509667200000}];
    return {reports};
  }
}
