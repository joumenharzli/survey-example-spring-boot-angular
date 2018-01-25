/*
 * Copyright (C) 2018 Joumen Harzli
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 */

import { Component, OnInit, Input, Output, EventEmitter, ChangeDetectionStrategy } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, Validators } from '@angular/forms';
import { findIndex, flatMap } from 'lodash';

import { UserResponse, Subject, Question } from '../../models/survey.models';

@Component({
  selector: 'app-survey-form',
  templateUrl: './survey-form.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SurveyFormComponent {

  form: FormGroup;

  _responses: UserResponse[];

  @Input()
  subjects: Subject[] = [];

  @Input()
  set responses(responses: UserResponse[]) {
    this._responses = responses;
    this.buildForm();
  }

  @Input()
  submitting: boolean;

  @Output()
  onSubmitForm: EventEmitter<{ responses: UserResponse[] }> = new EventEmitter();

  constructor(private formBuilder: FormBuilder) { }

  /**
   * Build the reponses form array by iterating over the provided responses
   * Every reponse is mapped by a form group
   *
   * @memberof AppComponent
   */
  buildForm() {
    this.form = this.formBuilder.group({ responses: this.formBuilder.array([]) });
    const questions = flatMap(this.subjects.map(subject => subject.questions));

    questions.forEach((question) => {
      (<FormArray>this.form.get('responses')).push(this.buildResponseFormGroup(question));
    });

  }

  submitForm(form) {
    this.onSubmitForm.emit(form.value);
  }

  /**
 * Create a form group that maps to the strcuture of the reponse
 *
 * @param {any} response reponse that will be mapped
 * @returns a form group with the mapped response
 * @memberof AppComponent
 */
  buildResponseFormGroup(question: Question) {

    const responseIndex = findIndex(this._responses, response => response.questionId === question.id);
    const response = this._responses[responseIndex];

    return this.formBuilder.group({
      questionId: question.id,
      content: this.formBuilder.control(
        response ? response.content : '', [Validators.required])
    });

  }

  /**
   * Get the index of the form group inside the form array
   * using the question id
   *
   * @param {number} questionId id of the question
   * @returns index of the form group
   * @throws Error if the form group was not found
   * @memberof AppComponent
   */
  getFormGroupIndexByQuestionId(questionId: number) {
    const index = findIndex(this.form.value.responses, response => response.questionId === questionId);

    if (index === -1) {
      throw new Error('No valid value was found for the response of the question with id ' + questionId);
    }

    return index;
  }

  /**
   * Used in the `ngFor` for better performance
   *
   * @param {any} entity entity iterated
   * @returns code of the entity
   * @memberof AppComponent
   */
  trackByFn(entity) {
    return entity.code;
  }

}
