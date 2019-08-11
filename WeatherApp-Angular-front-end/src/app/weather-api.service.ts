import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class WeatherApiService {

  constructor(private http: HttpClient) { }

  getWeather(location) {
    return this.http.get(
      'https://5c42d900.ngrok.io/api/weather/forecast?q=' + location
    );
  }
}
