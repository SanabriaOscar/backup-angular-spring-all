import { Component } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  title = 'App-Angular';
  curso ='El curso es angular';
  profesor='Oscar Sanabria Tavera';
}
