import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Championship } from '../entities/championship';
import { Pagination } from '../entities/pagination';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class ChampionshipService {

  private API_URL = 'http://localhost:8080/api/championship';
  private HTTP_OPTIONS = {
    headers: {
        authorization: 'Bearer ' + this.storage.get('access_token')
    }
  };

  constructor(private http: HttpClient, private storage: StorageService) { }

  getAllChampionships(pagination: string): Observable<Pagination> {
    {
      return this.http.get<Pagination>(this.API_URL+`/user/${this.storage.get('user_id')}`+pagination, this.HTTP_OPTIONS)
    }
  }

  getChampionshipById(id: number): Observable<Championship> {
    {
      return this.http.get<Championship>(this.API_URL+`/${id}`, this.HTTP_OPTIONS)
    }
  }

  getChampionshipsBySearch(search: string, ordination: any): Observable<Championship[]> {
    {
      return this.http.get<Championship[]>(this.API_URL+`/user/${this.storage.get('user_id')}/search?search=${search}&ordination=${ordination}`, this.HTTP_OPTIONS)
    }
  }

  addChampionship(championship: any) {
    return this.http.post<any>(this.API_URL+`/user/${this.storage.get('user_id')}`, championship, this.HTTP_OPTIONS); 
  }

  updateChampionship(championship: any, id: number) {
    return this.http.put<any>(this.API_URL+`/${id}`, championship, this.HTTP_OPTIONS); 
  }

  deleteChampionship(id: number) {
    return this.http.delete<any>(this.API_URL+`/${id}`, this.HTTP_OPTIONS); 
  }
}
