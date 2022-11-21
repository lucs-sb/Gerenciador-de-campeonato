import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NotifierService } from 'angular-notifier';
import { Group } from '../entities/group';
import { Team } from '../entities/team';
import { GroupInformationService } from '../services/group-information.service';
import { GroupService } from '../services/group.service';
import { StorageService } from '../services/storage.service';
import { TeamService } from '../services/team.service';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {
  private notifier: NotifierService;
  groups: Group[] = [];
  teams: Team[] = [];
  championshipId = this.storage.get('lastSeenChampionshipId');
  first_place: number = 0;
  second_place: number = 0;
  third_place: number = 0;
  fourth_place: number = 0;
  id: number = 0;
  first_place_points: number = 0;
  second_place_points: number = 0;
  third_place_points: number = 0;
  fourth_place_points: number = 0;
  formGroup = this.formBuilder.group({
    id: this.id,
    first_place: this.formBuilder.group({
      id: this.first_place
    }),
    second_place: this.formBuilder.group({
      id: this.second_place
    }),
    third_place: this.formBuilder.group({
      id: this.third_place
    }),
    fourth_place: this.formBuilder.group({
      id: this.fourth_place
    }),
    first_place_points: this.first_place_points,
    second_place_points: this.second_place_points,
    third_place_points: this.third_place_points,
    fourth_place_points: this.fourth_place_points
  });
  data: any;

  /**
   * Constructor
   *
   * @param {NotifierService} notifier Notifier service
   */
   constructor(notifier: NotifierService,
    private route: ActivatedRoute,
    private storage: StorageService,
    private formBuilder: FormBuilder,
    private router: Router,
    private groupService: GroupService,
    private groupInformationService: GroupInformationService,
    private teamService: TeamService) { this.notifier = notifier; }

  ngOnInit(): void {
    this.getGroupsByChampionship();
  }

  getGroupsByChampionship(): void{
    try {
      this.groupService.getGroups(this.championshipId).subscribe((res) => {
        this.groups = res;
        for(var i = 0; i < this.groups.length; i++)
          console.log(this.groups[i].group_information);
      },
      () => {
        this.notifier.notify('error', 'Não foi possível carregar os grupos no momento, tente novamente mais tarde');
      });
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  updateGroup(): void{
    try {
      this.formGroup = this.formBuilder.group({
        id: this.id,
        first_place: this.formBuilder.group({
          id: this.first_place
        }),
        second_place: this.formBuilder.group({
          id: this.second_place
        }),
        third_place: this.formBuilder.group({
          id: this.third_place
        }),
        fourth_place: this.formBuilder.group({
          id: this.fourth_place
        }),
        first_place_points: this.first_place_points,
        second_place_points: this.second_place_points,
        third_place_points: this.third_place_points,
        fourth_place_points: this.fourth_place_points
      });
      if(this.formGroup.value.first_place || this.formGroup.value.second_place || this.formGroup.value.third_place || this.formGroup.value.fourth_place ||
         this.formGroup.value.first_place_points || this.formGroup.value.second_place_points || this.formGroup.value.third_place_points || this.formGroup.value.fourth_place_points){
        this.data = this.formGroup.value;
          this.groupInformationService.updateGroupInformation(this.data).subscribe(() => {
            this.notifier.notify('success', 'grupo editado com sucesso');
            this.formGroup = this.formBuilder.group({
              id: 0,
              first_place: this.formBuilder.group({
                id: this.first_place
              }),
              second_place: this.formBuilder.group({
                id: this.second_place
              }),
              third_place: this.formBuilder.group({
                id: this.third_place
              }),
              fourth_place: this.formBuilder.group({
                id: this.fourth_place
              }),
              first_place_points: 0,
              second_place_points: 0,
              third_place_points: 0,
              fourth_place_points: 0
            });
            this.getGroupsByChampionship();
          }, () => {
            this.formGroup = this.formBuilder.group({
              id: 0,
              first_place: this.formBuilder.group({
                id: this.first_place
              }),
              second_place: this.formBuilder.group({
                id: this.second_place
              }),
              third_place: this.formBuilder.group({
                id: this.third_place
              }),
              fourth_place: this.formBuilder.group({
                id: this.fourth_place
              }),
              first_place_points: 0,
              second_place_points: 0,
              third_place_points: 0,
              fourth_place_points: 0
            });
            this.notifier.notify('error', 'Não foi possível editar o grupo campeonato no momento, tente novamente mais tarde');
          });
      }else{
        this.formGroup = this.formBuilder.group({
          id: 0,
          first_place: this.formBuilder.group({
            id: this.first_place
          }),
          second_place: this.formBuilder.group({
            id: this.second_place
          }),
          third_place: this.formBuilder.group({
            id: this.third_place
          }),
          fourth_place: this.formBuilder.group({
            id: this.fourth_place
          }),
          first_place_points: 0,
          second_place_points: 0,
          third_place_points: 0,
          fourth_place_points: 0
        });
        this.notifier.notify('error','Preencha algum campo');
      }
        
    } catch (ex: any) {
      this.notifier.notify('error', ex);
    }
  }

  handleTeamChange(event: any, number: number): void {
    var team = event.target.value;
    if(team){
      this.teamService.getTeamById(team.split(" - ")[0].split(": ")[1]).subscribe(res => {
        if(number == 1)
          this.first_place = res.id;
        else if(number == 2)
          this.second_place = res.id;
        else if(number == 3)
          this.third_place = res.id;
        else if(number == 4)
          this.fourth_place = res.id;
      });
    }
  }

  getTeams(teams: Team[], id: any): void{
    this.teams = teams;
    this.id = id;
  }
}
