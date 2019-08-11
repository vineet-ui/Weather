import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {WeatherApiService} from '../../weather-api.service';
import {City, List, WeatherResponse} from '../../models/WeatherResponse';
import {Router} from '@angular/router';

@Component({
  selector: 'app-weather',
  templateUrl: './weather.component.html',
  styleUrls: ['./weather.component.scss']
})
export class WeatherComponent implements OnInit {

  public weatherSearchForm: FormGroup;
  public weatherResponse: WeatherResponse;
  city: City;
  weatherList: List[];
  dataSize = 0;
  private isError = false;
  private error: string;

  constructor(private formBuilder: FormBuilder, private weatherService: WeatherApiService, private router: Router) {
  }

  ngOnInit() {
    this.weatherSearchForm = this.formBuilder.group({
      location: ['']
    });
  }


  getWeatherDetails(value: any) {
    console.log(value);

    this.weatherService
      .getWeather(value.location)
      .subscribe(data => {
          this.weatherResponse = <WeatherResponse>data;
          this.weatherList = this.weatherResponse.list;
          this.city = this.weatherResponse.city;
          this.dataSize = this.weatherList.length;

          // this.router.navigate(['']);
          console.log(data);
        },
        error => {
        this.dataSize = 0;
          this.error = error;
          this.isError = true;
        });
  }
}
