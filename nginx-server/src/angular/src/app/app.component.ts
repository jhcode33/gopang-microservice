import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  // The entered username
  username = '';
  // will hold a random theme for the chat component
  selectedTheme = '';
  // will hold a random avatar for the chat component
  selectedAvatar = '';
  // Detect when the user clicked on 'START'
  isReady = false;
  // List of themes and avatars to pass one randomly to the chat component
  themes = ['primary', 'warning', 'info', 'success'];
  avatars = [
    'https://mir-s3-cdn-cf.behance.net/user/115/24e8af100183223.59cbd13b396ba.png',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/default.png?raw=true',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/hs3.png?raw=true',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/hs4.png?raw=true',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/hs5.png?raw=true',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/hs6.png?raw=true',
    'https://github.com/Houssem-Selmi/booki/blob/master/Booki-Back-end/upload-dir/hs7.png?raw=true'
  ];

  // Select one random avatar and theme for every chat component
  constructor(private _router: Router, private http: HttpClient) {
    this.selectedTheme = this.getTheme();
    this.selectedAvatar = this.getAvatar();
  }

  // Get random theme
  getTheme() {
    return this.themes[Math.floor(Math.random() * this.themes.length)];
  }

  // Get random avatar
  getAvatar() {
    return this.avatars[Math.floor(Math.random() * this.avatars.length)];
  }

  // show the chat component
  chat() {
    this.isReady = true;
    this._router.navigateByUrl('http://localhost:8080/oauth2/oauth2/login');
    // this.http.get('http://your-api-url/data').subscribe(
    //   (response) => {
    //     // 요청 성공 시 응답 데이터 처리
    //     console.log('응답 데이터:', response);
    //     // 여기서 원하는 작업을 수행하거나 데이터를 처리할 수 있습니다.
    //   },
    //   (error) => {
    //     // 요청 실패 시 에러 처리
    //     console.error('에러 발생:', error);
    //   }
    // );
  }
}
