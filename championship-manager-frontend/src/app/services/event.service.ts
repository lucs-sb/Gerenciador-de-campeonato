import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Pagination } from '../entities/Pagination';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class EventService {

  private API_URL = 'http://localhost:8080/api/event';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getEvents(pagination: string, matchId: number): Observable<Pagination> {
    {
      return this.http.get<Pagination>(this.API_URL+`/match/${matchId}`+pagination, this.HTTP_OPTIONS)
    }
  }

  getEventsBySearch(search: string, matchId: number): Observable<Event[]> {
    {
      return this.http.get<Event[]>(this.API_URL+`/match/${matchId}/search?search=${search}`, this.HTTP_OPTIONS)
    }
  }

  addEvent(event: any, matchId: number) {
    return this.http.post<any>(this.API_URL+`/match/${matchId}`, event, this.HTTP_OPTIONS); 
  }

  updateEvent(event: any, id: number) {
    return this.http.patch<any>(this.API_URL+`/${id}`, event, this.HTTP_OPTIONS); 
  }

  deleteEvent(id: number) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
