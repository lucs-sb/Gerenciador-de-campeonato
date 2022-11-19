import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthenticatedGuard } from './authenticated.guard';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { TeamDetailComponent } from './team-detail/team-detail.component';
import { TeamComponent } from './team/team.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent, canActivate: [AuthenticatedGuard] },
  { path: 'teams', component: TeamComponent, canActivate: [AuthenticatedGuard] },
  { path: 'team/:id', component: TeamDetailComponent, canActivate: [AuthenticatedGuard] },
  { path: 'user', component: UserComponent, canActivate: [AuthenticatedGuard] },
  { path: '', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
