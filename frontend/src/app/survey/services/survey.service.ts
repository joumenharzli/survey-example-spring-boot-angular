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

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { UserResponse, Question, Subject } from '../models/survey.models';

@Injectable()
export class SurveyService {

  SUBJECT_API = 'http://localhost:8080/api/v1/subjects';
  QUESTION_API = 'http://localhost:8080/api/v1/questions';

  constructor(private httpClient: HttpClient) { }

  /**
   * Get all the subjects and their questions
   *
   * @returns {Observable<Question[]>} all the found subjects and their questions
   */
  getAllSubjectsAndQuestions(): Observable<Subject[]> {
    return this.httpClient.get<Subject[]>(this.SUBJECT_API);
  }

  /**
   * Get all responses of the connected user for the questions
   *
   * @param {number[]} questions ids of the questions that the user may responded
   * @returns {Observable<UserResponse[]>} the found responses of the connected user for the provided questions
   */
  getResponsesOfConnectedUserForQuestions(questions: number[]): Observable<UserResponse[]> {
    return this.httpClient.get<UserResponse[]>(this.QUESTION_API + '/' + questions.toString() + '/responses/me');
  }

  /**
   * Add and update the responses of the connected user for the provided questions
   *
   * @param {UserResponse[]} userResponses new responses of the user
   * @returns {Observable<UserResponse[]>} the list of the saved responses of the user
   */
  saveResponsesOfConnectedUserForQuestions(userResponses: UserResponse[]): Observable<UserResponse[]> {
    return this.httpClient.post<UserResponse[]>(this.QUESTION_API + '/responses/me', userResponses);
  }

}
