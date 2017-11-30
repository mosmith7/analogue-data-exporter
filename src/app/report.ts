export class Report {
  id: number;
  site: Site;
  channel: Channel;
  from: number;
  to: number;
}

class Site {
  id: number;
  name: string;
}

class Channel {
  id: number;
  name: string;
}
