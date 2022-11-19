import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Team } from '../entities/team';
import { StorageService } from '../services/storage.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
export class TeamComponent implements OnInit {
  private notifier: NotifierService;
  teams: Team[] = [];
  search: string = '';
  ordination: string = 'asc';

  formTeam = this.formBuilder.group({
    name: ['', [Validators.required]],
    abbreviation: ['', [Validators.required]],
    shield_img: ['', [Validators.required]]
  });

  data: any;

  currentIndex = -1;
  page = 0;
  count = 0;
  pageSize = 10;
  pageSizes = [10, 15, 20];
  totalPages = [0];

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private teamService: TeamService, 
    notifier: NotifierService, 
    private formBuilder: FormBuilder) { this.notifier = notifier; }

  ngOnInit(): void {
    this.retrieveTeams();
  }

  searchTeams(){
    try {
      this.teams = [];

      if("desc" == this.ordination || "asc" == this.ordination)
        this.ordination = "id,"+this.ordination
      
      this.teamService.getTeamsBySearch(this.search, "size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination).subscribe((res) => {
        this.teams = res.content;
        this.page = res.number;
        this.count = res.totalElements;
        this.pageSize = res.size;
        this.ordination = "asc";

        this.totalPages = [0];
        for(var i = 1; i < res.totalPages; i++)
          this.totalPages.push(i);
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  addTeam(): void{
    try {
      if(!this.formTeam.value.name || !this.formTeam.value.abbreviation || !this.formTeam.value.shield_img)
        this.notifier.notify('error','Preencha todos os campos');

      this.data = this.formTeam.value;
      this.teamService.addTeam(this.data).subscribe(() => {
        this.notifier.notify('success', 'Time criado com sucesso');
        this.formTeam = this.formBuilder.group({
          name: ['', [Validators.required]],
          abbreviation: ['', [Validators.required]],
          shield_img: ['', [Validators.required]]
        });
        this.retrieveTeams();
      }, () => {
        this.notifier.notify('error', 'Não foi possível criar um novo time no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handlePageChange(event: any): void {
    this.page = event.target.value;
    this.retrieveTeams();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.retrieveTeams();
  }

  retrieveTeams(): void{
    try {
      if("desc" == this.ordination || "asc" == this.ordination)
        this.ordination = "id,"+this.ordination

      this.teamService.getAllTeams("?size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination)
      .subscribe(
        res => {
          this.teams = res.content;
          this.page = res.number;
          this.count = res.totalElements;
          this.pageSize = res.size;
          this.ordination = "asc"
          this.totalPages = [0];
          
          for(var i = 0; i < res.totalPages; i++)
            this.totalPages[i] = i;
        },
        error => {
          console.log(error);
        });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }
}
