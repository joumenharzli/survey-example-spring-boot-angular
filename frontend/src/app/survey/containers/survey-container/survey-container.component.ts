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

import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, Validators } from '@angular/forms';
import { findIndex, flatMap } from 'lodash';
import 'rxjs/add/operator/switchMap';


import { SurveyService } from '../../services/survey.service';
import { Subject, UserResponse, Question } from '../../models/survey.models';

@Component({
  selector: 'app-survey-container',
  templateUrl: './survey-container.component.html'
})
export class SurveyContainerComponent implements OnInit {

  form: FormArray;
  loading = false;
  error = null;

  subjects: Subject[] = [];
  responses: UserResponse[] = [];

  constructor(private formBuilder: FormBuilder, private httpService: SurveyService) { }

  ngOnInit() {
    this.loadAllSubjectsAndQuestionsAndResponses();
  }

  loadAllSubjectsAndQuestionsAndResponses() {
    this.loading = true;
    this.error = null;
    this.httpService.getAllSubjectsAndQuestions()
      .switchMap(subjects => this.extractAndResponsesOfConnectedUserForQuestions(subjects),
      (subjects, responses) => ({ subjects: subjects, responses: responses }))
      .subscribe((result) => {
        this.subjects = result.subjects;
        this.responses = result.responses;
        this.buildForm();
        this.loading = false;
      }, (error) => this.handleHttpError(error));
  }

  extractAndResponsesOfConnectedUserForQuestions(subjects: Subject[]) {
    const questionIds = flatMap(subjects, subject => subject.questions).map(question => question.id);
    return this.httpService.getResponsesOfConnectedUserForQuestions(questionIds);
  }

  handleHttpError(error) {
    this.error = error;
    this.loading = false;
  }

  /**
   * Build the reponses form array by iterating over the provided responses
   * Every reponse is mapped by a form group
   *
   * @memberof AppComponent
   */
  buildForm() {
    this.form = this.formBuilder.array([]);
    const questions = flatMap(this.subjects.map(subject => subject.questions));

    questions.forEach((question) => {
      this.form.push(this.buildResponseFormGroup(question));
    });

  }

  /**
   * handle the form submition
   *
   * @param {FormArray} form instance of form
   * @memberof AppComponent
   */
  submitForm(form: FormArray) {
    const responses = form.value;
    this.httpService.saveResponsesOfConnectedUserForQuestions(responses)
      .subscribe((savedResponses) => {
        this.responses = savedResponses;
        this.buildForm();
        this.loading = false;
      }, (error) => this.handleHttpError(error));
  }

  /**
   * Create a form group that maps to the strcuture of the reponse
   *
   * @param {any} response reponse that will be mapped
   * @returns a form group with the mapped response
   * @memberof AppComponent
   */
  buildResponseFormGroup(question: Question) {

    const responseIndex = findIndex(this.responses, response => response.questionId === question.id);
    const response = this.responses[responseIndex];

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
    const index = findIndex(this.form.value, response => response.questionId === questionId);

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
