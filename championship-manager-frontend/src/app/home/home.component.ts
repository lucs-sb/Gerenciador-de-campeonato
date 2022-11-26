import { Component, OnInit } from '@angular/core';
import { NotifierService } from 'angular-notifier';
import { StorageService } from '../services/storage.service';
import { Championship } from '../entities/championship';
import { ChampionshipService } from '../services/championship.service';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Team } from '../entities/team';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  private notifier: NotifierService;
  championships: Championship[] = [];
  search: string = '';
  ordination: string = 'asc';

  formChampionship = this.formBuilder.group({
    name: ['', [Validators.required]],
    description: ['', [Validators.required]],
    award: ['', [Validators.required]],
    user: this.formBuilder.group({
      id: this.localStorage.get('user_id')
    }),
    teams: new FormBuilder().array([])
  });

  data: any;

  currentIndex = -1;
  page = 0;
  count = 0;
  pageSize = 10;
  pageSizes = [10, 15, 20];
  totalPages = [0];
  next = true;
  previous = true;
  teamsSelect: Team[] = [];

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private championshipService: ChampionshipService, 
    notifier: NotifierService, 
    private formBuilder: FormBuilder, 
    private localStorage: StorageService,
    private teamService: TeamService) { this.notifier = notifier; }

  ngOnInit(): void {
    this.retrieveChampionships();
  }

  searchChampionships(){
    try {
      this.championships = [];

      if("desc" == this.ordination || "asc" == this.ordination)
      this.ordination = "id,"+this.ordination

      this.championshipService.getChampionshipsBySearch(this.search, "size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination).subscribe((res) => {
        this.championships = res.content;
        this.page = res.number;
        this.count = res.totalElements;
        this.pageSize = res.size;
        this.ordination = "asc";
        this.totalPages = [0];

        for(var i = 1; i < res.totalPages; i++)
          this.totalPages.push(i);

        if(this.page == 0)
          this.previous = true;
        else
          this.previous = false;
    
        if(this.page == this.totalPages.length - 1)
          this.next = true;
        else
          this.next = false;

        for(var i = 0; i < this.totalPages.length; i++){
          if(document.getElementById(this.totalPages[i].toString())?.innerText != this.page.toString())
            document.getElementById(this.totalPages[i].toString())?.classList.remove("active");
          else
            document.getElementById(this.totalPages[i].toString())?.classList.add("active");
        }

        for(var j = 0; j < this.championships.length; j++){
          if(this.championships[j].status == 'IN_PROGRESS')
            this.championships[j].status = 'Em andamento'
          else if(this.championships[j].status == 'CLOSED')
            this.championships[j].status = 'Finalizado'
        }
      }, () => {
        this.notifier.notify('error', 'Não foi possível carregar os campeonatos no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  addChampionship(): void{
    try {
      if(!this.formChampionship.value.name || !this.formChampionship.value.description || !this.formChampionship.value.award || !this.formChampionship.value.teams)
        this.notifier.notify('error','Preencha todos os campos');

      else if(this.formChampionship.value.teams?.length! < 16)
        this.notifier.notify('error','Para inicar um novo campeonato é preciso selecionar 16 times');

      else{
        this.data = this.formChampionship.value;
        this.championshipService.addChampionship(this.data).subscribe(() => {
          this.notifier.notify('success', 'Campeonato criado com sucesso');
          this.formChampionship = this.formBuilder.group({
            name: ['', [Validators.required]],
            description: ['', [Validators.required]],
            award: ['', [Validators.required]],
            user: this.formBuilder.group({
              id: this.localStorage.get('user_id')
            }),
            teams: new FormBuilder().array([])
          });
          this.retrieveChampionships();
        }, () => {
          this.notifier.notify('error', 'Não foi possível criar um novo campeonato no momento, tente novamente mais tarde');
        });
      }
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handlePageChange(event: any): void {
    if('previous' == event)
      this.page -= 1;
    else if('next' == event)
      this.page += 1;
    else
      this.page = event;
    
    if(event == 0 || this.page == 0)
      this.previous = true;

    if(event == this.totalPages.length - 1 || this.page == this.totalPages.length - 1)
      this.next = true;
    
    for(var i = 0; i < this.totalPages.length; i++){
      if(document.getElementById(this.totalPages[i].toString())?.innerText != this.page.toString())
        document.getElementById(this.totalPages[i].toString())?.classList.remove("active");
      else
        document.getElementById(this.totalPages[i].toString())?.classList.add("active");
    }

    this.retrieveChampionships();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.retrieveChampionships();
  }

  retrieveChampionships(): void{
    try {
      if("desc" == this.ordination || "asc" == this.ordination)
      this.ordination = "id,"+this.ordination

      this.championshipService.getAllChampionships("?size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination)
      .subscribe(
        res => {
          this.championships = res.content;
          this.page = res.number;
          this.count = res.totalElements;
          this.pageSize = res.size;
          this.ordination = "asc"
          this.totalPages = [0];
          
          for(var i = 0; i < res.totalPages; i++)
            this.totalPages[i] = i;

          if(this.page == 0)
            this.previous = true;
          else
            this.previous = false;
      
          if(this.page == this.totalPages.length - 1)
            this.next = true;
          else
            this.next = false;

          for(var i = 0; i < this.totalPages.length; i++){
            if(document.getElementById(this.totalPages[i].toString())?.innerText != this.page.toString())
              document.getElementById(this.totalPages[i].toString())?.classList.remove("active");
            else
              document.getElementById(this.totalPages[i].toString())?.classList.add("active");
          }

          for(var j = 0; j < this.championships.length; j++){
            if(this.championships[j].status == 'IN_PROGRESS')
              this.championships[j].status = 'Em andamento'
            else if(this.championships[j].status == 'CLOSED')
              this.championships[j].status = 'Finalizado'
          }
        }, () => {
          this.notifier.notify('error', 'Não foi possível carregar os campeonatos no momento, tente novamente mais tarde');
        });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  getAllTeamsSelect(): void {
    try {
      this.teamsSelect = [];
      
      this.teamService.getAllTeams("?size=100"+"&page=0").subscribe((res) => {
        this.teamsSelect = res.content;
      }, () => {
        this.notifier.notify('error', 'Não foi possível carregar os times no momento, tente novamente mais tarde');
      });
    }catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handleTeamChange(event: any): void {
    var team = event.target.value;
    if(team){
      this.teamService.getTeamById(team.split(" - ")[0]).subscribe((res) => {
        this.formChampionship.value.teams?.push(res);
        this.teamsSelect.filter(team => {
          if(team.id == res.id)
            team.hidden = true
        });
      });
    }
  }
}
