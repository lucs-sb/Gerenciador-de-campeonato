import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class GroupInformationService {

  private API_URL = 'http://localhost:8080/api/group_information';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  updateGroupInformation(group_information: any) {
    return this.http.put<any>(this.API_URL, group_information, this.HTTP_OPTIONS); 
  }
}
