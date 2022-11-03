import { TestBed } from '@angular/core/testing';

import { HttpOauthInterceptor } from './http-oauth.interceptor';

describe('HttpOauthInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      HttpOauthInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: HttpOauthInterceptor = TestBed.inject(HttpOauthInterceptor);
    expect(interceptor).toBeTruthy();
  });
});