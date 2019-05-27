/**
 * Kino API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
import {MovieInfoDto} from './movieInfoDto';
import {SafeAccountInfoDto} from './safeAccountInfoDto';
import {SeatDto} from './seatDto';


export interface ReservationInfoDto { 
    seats?: Array<SeatDto>;
    id?: number;
    reservationDate?: Date;
    presentationId?: number;
    movie?: MovieInfoDto;
    account?: SafeAccountInfoDto;
}
