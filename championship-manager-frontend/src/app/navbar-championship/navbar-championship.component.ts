import { Component, OnInit } from '@angular/core';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-navbar-championship',
  templateUrl: './navbar-championship.component.html',
  styleUrls: ['./navbar-championship.component.css']
})
export class NavbarChampionshipComponent implements OnInit {

  championship_id: any = this.storage.get('lastSeenChampionshipId');

  constructor(private storage: StorageService) { }

  ngOnInit(): void {
  }

}
