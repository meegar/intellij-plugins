// Copyright 2000-2018 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import {Component} from '@angular/core';

@Component({
    selector: 'todo-cmp',
    templateUrl: "./definition.html",
    template: `<div>{{tit<caret>le = 1}}</div>`,
})
export class TodoCmp {
    title:string;
}
