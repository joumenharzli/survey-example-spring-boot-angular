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
import { MatSnackBar } from '@angular/material';
import { FieldError } from '../../../shared/models/field-error.model';
import { RestError } from '../../../shared/models/rest-error.model';

@Component({
  selector: 'app-survey-container',
  templateUrl: './survey-container.component.html'
})
export class SurveyContainerComponent implements OnInit {

  loading = false;
  submitting = false;
  error = null;

  subjects: Subject[] = [];
  responses: UserResponse[] = [];
  fieldsErrors: FieldError[];

  constructor(private httpService: SurveyService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.loadAllSubjectsAndQuestionsAndResponses();
  }

  loadAllSubjectsAndQuestionsAndResponses() {
    this.loading = true;
    this.error = null;
    this.httpService.getAllSubjectsAndQuestions()
      .switchMap(subjects => this.loadResponsesOfConnectedUserForQuestions(subjects),
      (subjects, responses) => ({ subjects: subjects, responses: responses }))
      .subscribe((result) => {
        this.subjects = result.subjects;
        this.responses = result.responses;
        this.loading = false;
      }, (error) => this.handleHttpError(error));
  }

  /**
   * handle the form submition
   *
   * @param responses of the user
   * @memberof AppComponent
   */
  submitForm(responses) {
    this.submitting = true;
    this.httpService.saveResponsesOfConnectedUserForQuestions(responses)
      .subscribe((savedResponses) => {
        this.responses = savedResponses;
        this.loading = false;
        this.submitting = false;
        this.openSnackBar('Responses submitted');
      }, (error) => this.handleHttpError(error));
  }

  loadResponsesOfConnectedUserForQuestions(subjects: Subject[]) {
    const questionIds = flatMap(subjects, subject => subject.questions).map(question => question.id);
    return this.httpService.getResponsesOfConnectedUserForQuestions(questionIds);
  }

  handleHttpError(errorResponse) {
    const error = errorResponse.error as RestError;

    if (error && error.message) {
      if (error.code === 'error.validation') {
        this.fieldsErrors = error.fieldsErrors;
      }
      const errorMessage = error.message;
      this.error = error;
      this.openSnackBar(errorMessage);
    } else {
      this.error = errorResponse;
      this.openSnackBar('Failed to connect to the server');
    }
    this.submitting = false;
    this.loading = false;
  }

  openSnackBar(message) {
    this.snackBar.open(message, 'Close', {
      duration: 2000,
      horizontalPosition: 'left'
    });
  }
}
