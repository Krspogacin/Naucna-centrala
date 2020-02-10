import { Injectable } from '@angular/core';
import { HttpClient, HttpEventType, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators'
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  constructor(private httpClient: HttpClient) { }

  public upload(data, taskId) {
    let uploadURL = '/api/scientific_work/upload/'.concat(taskId);

    return this.httpClient.post<any>(uploadURL, data, {
      reportProgress: true,
      observe: 'events'
    }).pipe(map((event) => {

      switch (event.type) {

        case HttpEventType.UploadProgress:
          const progress = Math.round(100 * event.loaded / event.total);
          return { status: 'progress', message: progress };

        case HttpEventType.Response:
          return { status: 'finish', message: 'succesfully uploaded' };
        default:
          return `Unhandled event: ${event.type}`;
      }
    })
    );
  }

  public download(taskId) {
    return this.httpClient.get('/api/scientific_work/download/'.concat(taskId), { responseType: 'blob' });
  }
}
