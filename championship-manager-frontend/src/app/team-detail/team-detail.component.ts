import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';
import { NotifierService } from 'angular-notifier';
import { Player } from '../entities/player';
import { Team } from '../entities/team';
import { PlayerService } from '../services/player.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-team-detail',
  templateUrl: './team-detail.component.html',
  styleUrls: ['./team-detail.component.css']
})
export class TeamDetailComponent implements OnInit {

  private notifier: NotifierService;
  players: Player[] = [];
  team: Team | undefined;
  //search: string = '';
  ordination: string = 'asc';

  formTeam = this.formBuilder.group({
    name: '',
    abbreviation: '',
    shield_img: ''
  });

  formPlayer = this.formBuilder.group({
    id: 0,
    name: '',
    team: this.formBuilder.group({
      id: this.route.snapshot.paramMap.get('id')!
    })
  });

  data: any;

  currentIndex = -1;
  page = 0;
  count = 0;
  pageSize = 10;
  pageSizes = [15, 20, 25];
  totalPages = [0];
  player_id: number = 0;
  next = true;
  previous = true;

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
  constructor(private teamService: TeamService, 
    private playerService: PlayerService,
    notifier: NotifierService, 
    private route: ActivatedRoute,
    private location: Location,
    private router: Router,
    private formBuilder: FormBuilder) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getTeamById();
    this.getPlayersByTeam();
  }

  getPlayersByTeam(): void{
    try {
      if("desc" == this.ordination || "asc" == this.ordination)
      this.ordination = "id,"+this.ordination

      const team_id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);

      this.playerService.getAllPlayersByTeam(team_id, "?size="+this.pageSize+"&page="+this.page+"&sort="+this.ordination)
      .subscribe(
        res => {
          this.players = res.content;
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
        }, () => {
          this.notifier.notify('error', 'Não foi possível carregar os jogadores no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  getTeamById(): void{
    try {
      const team_id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);

      this.teamService.getTeamById(team_id)
      .subscribe(
        (res) => (this.team = res), () => {
          this.notifier.notify('error', 'Não foi possível carregar o time no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updatePlayer(): void{
    try {
      if(this.formPlayer.value.name){
        this.formPlayer.value.id = this.player_id;
        this.data = this.formPlayer.value;

        this.playerService.updatePlayer(this.data).subscribe(() => {
          this.notifier.notify('success', 'Jogador editado com sucesso');
          this.formPlayer = this.formBuilder.group({
            id: 0,
            name: '',
            team: this.formBuilder.group({
              id: this.route.snapshot.paramMap.get('id')!
            })
          });
          this.player_id = 0;
          this.getPlayersByTeam();
        }, () => {
          this.notifier.notify('error', 'Não foi possível editar o jogador no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Informe o nome do jogador');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updateTeam(): void{
    try {
      if(this.formTeam.value.name || this.formTeam.value.abbreviation || this.formTeam.value.shield_img){
        if(!this.formTeam.value.name)
          this.formTeam.value.name = this.team?.name;
        if(!this.formTeam.value.abbreviation)
          this.formTeam.value.abbreviation = this.team?.abbreviation;
        if(!this.formTeam.value.shield_img)
          this.formTeam.value.shield_img = this.team?.shield_img;

        this.data = this.formTeam.value;

        const team_id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);

        this.teamService.updateTeam(this.data, team_id).subscribe(() => {
          this.notifier.notify('success', 'Time editado com sucesso');
          this.formTeam = this.formBuilder.group({
            name: '',
            abbreviation: '',
            shield_img: ''
          });
          this.getTeamById();
        }, () => {
          this.notifier.notify('error', 'Não foi possível editar o time no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Preencha algum campo');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  deletePlayer(player: Player): void {
    try {
      this.playerService.deletePlayer(player.id)
      .subscribe(
        () => (this.getPlayersByTeam()), () => {
          this.notifier.notify('error', 'Não foi possível deletar o jogador no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  deleteTeam(): void {
    try {
      const team_id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);

      this.teamService.deleteTeam(team_id)
      .subscribe(
        () => (this.router.navigate(['/teams'])), () => {
          this.notifier.notify('error', 'Não foi possível deletar o time no momento, tente novamente mais tarde');
        });
    } catch (ex: any) {
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

    this.getPlayersByTeam();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.getPlayersByTeam();
  }

  addPlayer(): void{
    try {
      if(this.formPlayer.value.name){
        this.data = this.formPlayer.value;

        this.playerService.addPlayer(this.data).subscribe(() => {
          this.notifier.notify('success', 'Jogador criado com sucesso');
          this.formPlayer = this.formBuilder.group({
            id: 0,
            name: '',
            team: this.formBuilder.group({
              id: this.route.snapshot.paramMap.get('id')!
            })
          });
          this.getPlayersByTeam();
        }, () => {
          this.notifier.notify('error', 'Não foi possível criar um novo jogador no momento, tente novamente mais tarde');
        });
      }else
        this.notifier.notify('error','Informe o nome do jogador');
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  setPlayerId(id: number): void{
    this.player_id = id;
  }

  goBack(): void {
    this.location.back();
  }
}
