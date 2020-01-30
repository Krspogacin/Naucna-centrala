import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) { }

  startProcess(editor) {
    return this.httpClient.get('/api/magazine/startProcess/'.concat(editor));
  }

  getMagazines(editor) {
    return this.httpClient.get('/api/magazine/editor/'.concat(editor));
  }

  getActiveMagazines() {
    return this.httpClient.get('/api/magazine');
  }

  getMagazine(id) {
    return this.httpClient.get('/api/magazine/'.concat(id));
  }

  getEditions(id, username) {
    return this.httpClient.get('/api/magazine/editions/'.concat(id) + '/'.concat(username));
  }

  getEdition(id) {
    return this.httpClient.get('/api/magazine/edition/'.concat(id));
  }

  getWorks(id, username) {
    return this.httpClient.get('/api/magazine/works/'.concat(id) + '/'.concat(username));
  }

  saveEdition(id, edition) {
    return this.httpClient.post('/api/magazine/save_edition/'.concat(id), edition);
  }

  saveMagazine(formResults, taskId) {
    return this.httpClient.post('/api/magazine/'.concat(taskId), formResults);
  }

  getERFormFields(processInstanceId, editor) {
    return this.httpClient.get('/api/magazine/editors_and_reviewers_form_fields/'.concat(processInstanceId).concat('/').concat(editor));
  }

  saveER(ERDto, taskId) {
    return this.httpClient.post('/api/magazine/save_editors_and_reviewers/'.concat(taskId), ERDto);
  }

  getMagazineTasks() {
    return this.httpClient.get('/api/magazine/check_magazine');
  }

  getMagazineFormFields(taskId) {
    return this.httpClient.get('/api/magazine/check_magazine/'.concat(taskId));
  }

  checkMagazine(checkReviewerDto) {
    return this.httpClient.post('/api/magazine/check_magazine', checkReviewerDto);
  }

  getUpdatedFormFields(processInstanceId) {
    return this.httpClient.get('/api/magazine/edited/'.concat(processInstanceId));
  }
}
