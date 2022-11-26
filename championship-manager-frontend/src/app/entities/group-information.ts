import { Team } from "./team";

export interface GroupInformation{
    id: number;
    first_place: Team;
    second_place: Team;
    third_place: Team;
    fourth_place: Team;
    first_place_points: number;
    second_place_points: number;
    third_place_points: number;
    fourth_place_points: number;
}