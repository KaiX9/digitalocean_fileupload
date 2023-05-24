import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable } from "rxjs";

const URL_RAILWAY = 'https://fileupload-production-947d.up.railway.app/upload'
// const URL_LOCAL = 'http://localhost:8080/upload'

@Injectable()
export class UploadService {

    http = inject(HttpClient)

    upload(title: string, file: File): Observable<any> {
        const formData = new FormData();
        // @RequestPart String title 
        formData.set('title', title);
        // @RequestPart MultipartFile myFile
        formData.set('myFile', file);

        return this.http.post<any>(URL_RAILWAY, formData);
    }
}