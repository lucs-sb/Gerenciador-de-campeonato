import { Group } from "./group";
import { Team } from "./team";
import { User } from "./user";

export interface Championship{
    id: number;
    description: string;
    name: string;
    number_of_teams: number;
    award: string;
    status: string;
    user: User;
    teams: Team[];
    groups: Group[];
}