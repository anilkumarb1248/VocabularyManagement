import { Component, OnInit } from '@angular/core';
import { ListResponse } from 'src/app/models/list-response';
import { VocabularyHeader } from 'src/app/models/vocabulary-header';
import { VocabularHeaderService } from 'src/app/services/vocabular-header.service';

import { NestedTreeControl } from '@angular/cdk/tree';
import { MatTreeNestedDataSource } from '@angular/material/tree';

@Component({
  selector: 'app-vocabulary-headers',
  templateUrl: './vocabulary-headers.component.html',
  styleUrls: ['./vocabulary-headers.component.scss']
})
export class VocabularyHeadersComponent implements OnInit {

  listResponse: ListResponse<VocabularyHeader> | undefined;

  public treeControl = new NestedTreeControl<VocabularyHeader>((node) => node.childHeaders);
  public dataSource = new MatTreeNestedDataSource<VocabularyHeader>();
  

  constructor(
    private vocabularHeaderService: VocabularHeaderService
    ) { }

  ngOnInit(): void {
    this.loadAllParentHeaders();
  }

  hasChild = (_: number, node: VocabularyHeader) =>
    !!node.childHeaders && node.childHeaders.length > 0;

  loadAllParentHeaders() {
    this.vocabularHeaderService.getAllParentVocabularyHeaders().subscribe(
      (data) => {
        this.listResponse = data;
        if(this.listResponse.status.toString() == "SUCCESS"){

          let childeHs: VocabularyHeader[] = new Array();
          let a = new VocabularyHeader();
          a.headerId = 10;
          a.header = "News"
          childeHs.push(a);
      
          let b = new VocabularyHeader();
          b.headerId = 20;
          b.header = "Articles"
          childeHs.push(b);

          this.listResponse.values[0].childHeaders = childeHs;

          this.dataSource.data = data.values;
        }else{
          console.log(this.listResponse.getMsg());
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }

}
