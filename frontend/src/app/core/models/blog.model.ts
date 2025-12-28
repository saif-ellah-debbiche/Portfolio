export interface Blog{
    id:number;
    title:string;
    content:string;
    contentUrl?: string; // used when contentSource = 'url'
    summary:string;
    date:Date;
    author:string;
    createdAt:Date;
    tags:string;
    defaultImageLink:string
}