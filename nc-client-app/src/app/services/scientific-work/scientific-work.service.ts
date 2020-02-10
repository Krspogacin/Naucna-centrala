import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ScientificWorkService {

  constructor(private httpClient: HttpClient) { }

  startProcess(username) {
    return this.httpClient.get('/api/scientific_work/startProcess/'.concat(username));
  }

  saveScientificWorkMagazine(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/'.concat(taskId), formResults);
  }

  getInfoFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/scientific_work_info/'.concat(processInstanceId) + '/'.concat(username));
  }

  saveScientificWork(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/scientific_work_info/'.concat(taskId), formResults);
  }

  getCoauthorFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/coauthors/'.concat(processInstanceId) + '/'.concat(username));
  }

  saveCoauthor(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/coauthors/'.concat(taskId), formResults);
  }

  getPdfFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/pdf/'.concat(processInstanceId + '/'.concat(username)));
  }

  getCheckScientificWorksTasks(username) {
    return this.httpClient.get('/api/scientific_work/scientific_work_tasks/'.concat(username));
  }

  checkScientificWorkFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/scientific_work_tasks/'.concat(taskId) + '/'.concat(username));
  }

  checkScientificWork(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/scientific_work_tasks/'.concat(taskId), formResults);
  }

  getCheckPdfTasks(username) {
    return this.httpClient.get('/api/scientific_work/pdf_tasks/'.concat(username));
  }

  checkPdfFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/pdf_tasks/'.concat(taskId) + '/'.concat(username));
  }

  checkPdf(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/pdf_tasks/'.concat(taskId), formResults);
  }

  setTimeFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/set_time/'.concat(processInstanceId) + '/'.concat(username));
  }

  setTime(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/set_time/'.concat(taskId), formResults);
  }

  getChangeFormatTasks(username) {
    return this.httpClient.get('/api/scientific_work/change_format/'.concat(username));
  }

  changeFormatFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/change_format/'.concat(taskId) + '/'.concat(username));
  }

  changeFormat(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/change_format/'.concat(taskId), formResults);
  }

  getSelectReviewersTasks(username) {
    return this.httpClient.get('/api/scientific_work/select_reviewer/'.concat(username));
  }

  selectReviewersFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/select_reviewer/'.concat(taskId) + '/'.concat(username));
  }

  selectReviewers(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/select_reviewer/'.concat(taskId), formResults);
  }

  getReviewerSetTimeTasks(username) {
    return this.httpClient.get('/api/scientific_work/reviewer_set_time/'.concat(username));
  }

  reviewrsSetTimeFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/reviewer_set_time/'.concat(taskId) + '/'.concat(username));
  }

  reviewersSetTime(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/reviewer_set_time/'.concat(taskId), formResults);
  }

  getReviewTasks(username) {
    return this.httpClient.get('/api/scientific_work/review/'.concat(username));
  }

  reviewFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/review/'.concat(taskId) + '/'.concat(username));
  }

  review(formResults, taskId, username) {
    return this.httpClient.post('/api/scientific_work/review/'.concat(taskId) + '/'.concat(username), formResults);
  }

  getReviewResultsTasks(username) {
    return this.httpClient.get('/api/scientific_work/review_results/'.concat(username));
  }

  reviewResultsFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/review_results/'.concat(taskId) + '/'.concat(username));
  }

  reviewResults(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/review_results/'.concat(taskId), formResults);
  }

  getEditorDecisionTasks(username) {
    return this.httpClient.get('/api/scientific_work/editor_decision/'.concat(username));
  }

  editorDecisionFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/editor_decision/'.concat(taskId) + '/'.concat(username));
  }

  editorDecision(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/editor_decision/'.concat(taskId), formResults);
  }

  changingTimeFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/set_changing_time/'.concat(processInstanceId) + '/'.concat(username));
  }

  setChangingTime(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/set_changing_time/'.concat(taskId), formResults);
  }

  getReviewerCommentTasks(username) {
    return this.httpClient.get('/api/scientific_work/reviewer_comment/'.concat(username));
  }

  reviewerCommentFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/reviewer_comment/'.concat(taskId) + '/'.concat(username));
  }

  reviewerComment(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/reviewer_comment/'.concat(taskId), formResults);
  }

  getChangePdfTasks(username) {
    return this.httpClient.get('/api/scientific_work/change_pdf/'.concat(username));
  }

  changePdfFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/change_pdf/'.concat(taskId) + '/'.concat(username));
  }

  getViewingWorkTasks(username) {
    return this.httpClient.get('/api/scientific_work/viewing_work/'.concat(username));
  }

  viewingWorkFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/viewing_work/'.concat(taskId) + '/'.concat(username));
  }

  viewingWork(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/viewing_work/'.concat(taskId), formResults);
  }

  changingTimeAgainFormFields(processInstanceId, username) {
    return this.httpClient.get('/api/scientific_work/set_changing_time_again/'.concat(processInstanceId) + '/'.concat(username));
  }

  setChangingTimeAgain(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/set_changing_time_again/'.concat(taskId), formResults);
  }

  getNewReviewersTasks(username) {
    return this.httpClient.get('/api/scientific_work/new_reviewers/'.concat(username));
  }

  newReviewersFormFields(taskId, username) {
    return this.httpClient.get('/api/scientific_work/new_reviewers/'.concat(taskId) + '/'.concat(username));
  }

  newReviewers(formResults, taskId) {
    return this.httpClient.post('/api/scientific_work/new_reviewers/'.concat(taskId), formResults);
  }
}
