import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  registerUser(user, taskId) {
    return this.httpClient.post('/api/registration/'.concat(taskId), user);
  }

  verifyUser(username, processInstanceId) {
    return this.httpClient.post('/api/registration/verify/'.concat(processInstanceId).concat('/').concat(username), null);
  }

  checkReviewer(checkReviewerDto) {
    return this.httpClient.post('/api/registration/check_reviewer', checkReviewerDto);
  }
}
