import { TestBed } from '@angular/core/testing';

import { EventLayout } from './event-layout.service';

describe('EventLayout.Service', () => {
  let service: EventLayout;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EventLayout);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
