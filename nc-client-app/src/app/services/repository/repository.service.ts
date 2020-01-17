import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  constructor(private httpClient: HttpClient) { }

  startProcess() {
    return this.httpClient.get('/api/registration/startProcess');
  }

  getReviewerTasks() {
    return this.httpClient.get('/api/registration/check_reviewer');
  }

  getReviewerFormFields(taskId) {
    return this.httpClient.get('/api/registration/check_reviewer/'.concat(taskId));
  }
}
