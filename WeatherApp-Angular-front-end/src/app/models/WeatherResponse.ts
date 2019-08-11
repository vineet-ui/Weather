export class WeatherResponse {
  city: City;
  list: List[];
}

export class City {
  name: string;
}


export class List {

  dt: string;
  pressure: number;
  humidity: number;
  temp: any;
  weather: WeatherData[];
  wind: Wind;
  clouds: number;
  rain: number;
  snow: number;

}

export class WeatherData {
  main: string;
  description: string;
  icon: string;
}
export class Wind {
  speed: number;
  deg: number;
}
