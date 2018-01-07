import { Component, ChangeDetectionStrategy } from '@angular/core';
import { FormBuilder, FormArray, Validators } from '@angular/forms';
import { staticQuestions, staticResponses } from './static-values';

/**
 * A simple component that demonstrate a solution for creating surveys using Angular
 *
 * @export
 * @class AppComponent
 * @author Joumen Ali HARZLI
 */
@Component({
  selector: 'app-survey',
  templateUrl: 'app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})

export class AppComponent {

  form: FormArray;
  questions = staticQuestions;
  responses = staticResponses;

  constructor(private formBuilder: FormBuilder) {
    this.buildForm();
  }

  /**
   * Build the reponses form array by iterating over the provided responses
   * Every reponse is mapped by a form group
   *
   * @memberof AppComponent
   */
  buildForm() {
    this.form = this.formBuilder.array([]);

    this.responses.forEach((response) => {
      this.form.push(this.buildResponseFormGroup(response));
    });

  }

  /**
   * handle the form submition
   *
   * @param {FormArray} form instance of form
   * @memberof AppComponent
   */
  submitForm(form: FormArray) {
    console.log(form.value);
  }

  /**
   * Create a form group that maps to the strcuture of the reponse
   *
   * @param {any} response reponse that will be mapped
   * @returns a form group with the mapped response
   * @memberof AppComponent
   */
  buildResponseFormGroup(response) {

    return this.formBuilder.group({
      questionCode: response.questionCode,
      responseCode: response.responseCode,
      value: this.formBuilder.control(
        response.value ? response.value : '', [Validators.required])
    });

  }

  /**
   * Get the index of the form group inside the form array
   * using the question and reponse code
   *
   * @param {string} questionCode code of the question
   * @param {string} responseCode code of the reponse
   * @returns index of the form group
   * @throws Error if the form group was not found
   * @memberof AppComponent
   * TODO: replace this with loadash findIndex for better performance
   */
  getFormGroupIndexByQuestionAndResponseCode(questionCode: string, responseCode: string) {
    const formValue = this.form.value as Array<any>;

    let index = 0;
    let found = false;

    while (index < formValue.length && !found) {
      if (formValue[index].questionCode === questionCode && formValue[index].responseCode === responseCode) {
        found = true;
      } else {
        index++;
      }
    }

    if (!found) {
      throw new Error('No valid value was found for the reponse ' + responseCode + ' and question ' + questionCode);
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

