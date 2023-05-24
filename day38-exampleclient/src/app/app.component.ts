import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { firstValueFrom } from 'rxjs';
import { UploadService } from './upload.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  form!: FormGroup
  fb = inject(FormBuilder)

  uploadSvc = inject(UploadService)

  @ViewChild('uploadFile')
  uploadFile!: ElementRef

  ngOnInit(): void {
    this.form = this.fb.group({
      title: this.fb.control<string>( '', [ Validators.required ] ),
      file: this.fb.control<File | null>( null, [ Validators.required ] )
    })
  }

  upload() {
    const f: File = this.uploadFile.nativeElement.files[0];
    const data = this.form.value;
    console.info('>>> data: ', data);
    console.info('>>> file: ', f);

    firstValueFrom(this.uploadSvc.upload(data.title, f))
      .then(result => {
        alert('uploaded')
        this.form.reset()
      })
      .catch(err => {
        alert(JSON.stringify(err))
      })
  }
  
}
