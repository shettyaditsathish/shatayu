/* eslint-disable */
/* tslint:disable */
// @ts-nocheck
/*
 * ---------------------------------------------------------------
 * ## THIS FILE WAS GENERATED VIA SWAGGER-TYPESCRIPT-API        ##
 * ##                                                           ##
 * ## AUTHOR: acacode                                           ##
 * ## SOURCE: https://github.com/acacode/swagger-typescript-api ##
 * ---------------------------------------------------------------
 */

export interface Drug {
  /** @format int64 */
  drugId?: number;
  drugName?: string;
  /** @format float */
  drugCost?: number;
  contents?: string;
  /** @format int64 */
  quantity?: number;
  details?: string;
  inactive?: string;
}

export interface Patient {
  /** @format int64 */
  patientId?: number;
  firstName?: string;
  lastName?: string;
  middleName?: string;
  /** @format int64 */
  age?: number;
  sex?: string;
  /** @format date-time */
  initialDate?: string;
  landline?: string;
  knownCases?: string;
  history?: string;
  address?: string;
  mobile1?: string;
  mobile2?: string;
}

export interface Treatment {
  /** @format int64 */
  treatmentId?: number;
  /** @format int64 */
  patientId?: number;
  patient?: Patient;
  /** @format date-time */
  treatmentDate?: string;
  signsSymptoms?: string;
  tongue?: string;
  pulse?: string;
  results?: string;
  paid?: string;
  /** @format float */
  amountPaid?: number;
  paymentType?: string;
  /** @format float */
  balance?: number;
  treatmentDrugs?: TreatmentDrug[];
}

export interface TreatmentDrug {
  /** @format int64 */
  treatmentDrugId?: number;
  drug?: Drug;
  duration?: string;
  /** @format int32 */
  noOfDays?: number;
}

export interface Panchkarma {
  /** @format int64 */
  panchkarmaId?: number;
  panchkarmaName?: string;
  /** @format float */
  cost?: number;
  procd?: string;
  diseases?: string;
  details?: string;
  /** @format int64 */
  noOfDays?: number;
}

export interface PatientPanchkarma {
  /** @format int64 */
  patientPanchId?: number;
  panchkarma?: Panchkarma;
  /** @format int64 */
  patientId?: number;
  patient?: Patient;
  /** @format date-time */
  startDate?: string;
  /** @format date-time */
  endDate?: string;
  results?: string;
  /** @format float */
  amountPaid?: number;
  paid?: string;
  paymentType?: string;
  /** @format float */
  balance?: number;
}

export interface PageTreatment {
  /** @format int32 */
  totalPages?: number;
  /** @format int64 */
  totalElements?: number;
  /** @format int32 */
  numberOfElements?: number;
  first?: boolean;
  last?: boolean;
  pageable?: PageableObject;
  /** @format int32 */
  size?: number;
  content?: Treatment[];
  /** @format int32 */
  number?: number;
  sort?: SortObject;
  empty?: boolean;
}

export interface PageableObject {
  paged?: boolean;
  /** @format int32 */
  pageNumber?: number;
  /** @format int32 */
  pageSize?: number;
  unpaged?: boolean;
  /** @format int64 */
  offset?: number;
  sort?: SortObject;
}

export interface SortObject {
  sorted?: boolean;
  unsorted?: boolean;
  empty?: boolean;
}

export interface PagePatient {
  /** @format int32 */
  totalPages?: number;
  /** @format int64 */
  totalElements?: number;
  /** @format int32 */
  numberOfElements?: number;
  first?: boolean;
  last?: boolean;
  pageable?: PageableObject;
  /** @format int32 */
  size?: number;
  content?: Patient[];
  /** @format int32 */
  number?: number;
  sort?: SortObject;
  empty?: boolean;
}

export interface PagePatientPanchkarma {
  /** @format int32 */
  totalPages?: number;
  /** @format int64 */
  totalElements?: number;
  /** @format int32 */
  numberOfElements?: number;
  first?: boolean;
  last?: boolean;
  pageable?: PageableObject;
  /** @format int32 */
  size?: number;
  content?: PatientPanchkarma[];
  /** @format int32 */
  number?: number;
  sort?: SortObject;
  empty?: boolean;
}

import type {
  AxiosInstance,
  AxiosRequestConfig,
  AxiosResponse,
  HeadersDefaults,
  ResponseType,
} from "axios";
import axios from "axios";

export type QueryParamsType = Record<string | number, any>;

export interface FullRequestParams
  extends Omit<AxiosRequestConfig, "data" | "params" | "url" | "responseType"> {
  /** set parameter to `true` for call `securityWorker` for this request */
  secure?: boolean;
  /** request path */
  path: string;
  /** content type of request body */
  type?: ContentType;
  /** query params */
  query?: QueryParamsType;
  /** format of response (i.e. response.json() -> format: "json") */
  format?: ResponseType;
  /** request body */
  body?: unknown;
}

export type RequestParams = Omit<
  FullRequestParams,
  "body" | "method" | "query" | "path"
>;

export interface ApiConfig<SecurityDataType = unknown>
  extends Omit<AxiosRequestConfig, "data" | "cancelToken"> {
  securityWorker?: (
    securityData: SecurityDataType | null,
  ) => Promise<AxiosRequestConfig | void> | AxiosRequestConfig | void;
  secure?: boolean;
  format?: ResponseType;
}

export enum ContentType {
  Json = "application/json",
  JsonApi = "application/vnd.api+json",
  FormData = "multipart/form-data",
  UrlEncoded = "application/x-www-form-urlencoded",
  Text = "text/plain",
}

export class HttpClient<SecurityDataType = unknown> {
  public instance: AxiosInstance;
  private securityData: SecurityDataType | null = null;
  private securityWorker?: ApiConfig<SecurityDataType>["securityWorker"];
  private secure?: boolean;
  private format?: ResponseType;

  constructor({
    securityWorker,
    secure,
    format,
    ...axiosConfig
  }: ApiConfig<SecurityDataType> = {}) {
    this.instance = axios.create({
      ...axiosConfig,
      baseURL: axiosConfig.baseURL || "http://localhost:8080",
    });
    this.secure = secure;
    this.format = format;
    this.securityWorker = securityWorker;
  }

  public setSecurityData = (data: SecurityDataType | null) => {
    this.securityData = data;
  };

  protected mergeRequestParams(
    params1: AxiosRequestConfig,
    params2?: AxiosRequestConfig,
  ): AxiosRequestConfig {
    const method = params1.method || (params2 && params2.method);

    return {
      ...this.instance.defaults,
      ...params1,
      ...(params2 || {}),
      headers: {
        ...((method &&
          this.instance.defaults.headers[
            method.toLowerCase() as keyof HeadersDefaults
          ]) ||
          {}),
        ...(params1.headers || {}),
        ...((params2 && params2.headers) || {}),
      },
    };
  }

  protected stringifyFormItem(formItem: unknown) {
    if (typeof formItem === "object" && formItem !== null) {
      return JSON.stringify(formItem);
    } else {
      return `${formItem}`;
    }
  }

  protected createFormData(input: Record<string, unknown>): FormData {
    if (input instanceof FormData) {
      return input;
    }
    return Object.keys(input || {}).reduce((formData, key) => {
      const property = input[key];
      const propertyContent: any[] =
        property instanceof Array ? property : [property];

      for (const formItem of propertyContent) {
        const isFileType = formItem instanceof Blob || formItem instanceof File;
        formData.append(
          key,
          isFileType ? formItem : this.stringifyFormItem(formItem),
        );
      }

      return formData;
    }, new FormData());
  }

  public request = async <T = any, _E = any>({
    secure,
    path,
    type,
    query,
    format,
    body,
    ...params
  }: FullRequestParams): Promise<AxiosResponse<T>> => {
    const secureParams =
      ((typeof secure === "boolean" ? secure : this.secure) &&
        this.securityWorker &&
        (await this.securityWorker(this.securityData))) ||
      {};
    const requestParams = this.mergeRequestParams(params, secureParams);
    const responseFormat = format || this.format || undefined;

    if (
      type === ContentType.FormData &&
      body &&
      body !== null &&
      typeof body === "object"
    ) {
      body = this.createFormData(body as Record<string, unknown>);
    }

    if (
      type === ContentType.Text &&
      body &&
      body !== null &&
      typeof body !== "string"
    ) {
      body = JSON.stringify(body);
    }

    return this.instance.request({
      ...requestParams,
      headers: {
        ...(requestParams.headers || {}),
        ...(type ? { "Content-Type": type } : {}),
      },
      params: query,
      responseType: responseFormat,
      data: body,
      url: path,
    });
  };
}

/**
 * @title OpenAPI definition
 * @version v0
 * @baseUrl http://localhost:8080
 */
export class Api<
  SecurityDataType extends unknown,
> extends HttpClient<SecurityDataType> {
  treatment = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name SaveTreatment
     * @request POST:/treatment
     */
    saveTreatment: (data: Treatment, params: RequestParams = {}) =>
      this.request<Treatment, any>({
        path: `/treatment`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),
  };
  patient = {
    /**
     * No description
     *
     * @tags patient-controller
     * @name SavePatient
     * @request POST:/patient
     */
    savePatient: (data: Patient, params: RequestParams = {}) =>
      this.request<Patient, any>({
        path: `/patient`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),
  };
  patientPanchkarma = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name SavePatientPanchkarma
     * @request POST:/patientPanchkarma
     */
    savePatientPanchkarma: (
      data: PatientPanchkarma,
      params: RequestParams = {},
    ) =>
      this.request<PatientPanchkarma, any>({
        path: `/patientPanchkarma`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),
  };
  drug = {
    /**
     * No description
     *
     * @tags drug-controller
     * @name SaveDrug
     * @request POST:/drug
     */
    saveDrug: (data: Drug, params: RequestParams = {}) =>
      this.request<Drug, any>({
        path: `/drug`,
        method: "POST",
        body: data,
        type: ContentType.Json,
        ...params,
      }),

    /**
     * No description
     *
     * @tags drug-controller
     * @name DeleteDrug
     * @request DELETE:/drug/{drugId}
     */
    deleteDrug: (drugId: number, params: RequestParams = {}) =>
      this.request<void, any>({
        path: `/drug/${drugId}`,
        method: "DELETE",
        ...params,
      }),
  };
  treatments = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name GetTreatmentsByPatientId
     * @request GET:/treatments
     */
    getTreatmentsByPatientId: (
      query: {
        /** @format int64 */
        patientId: number;
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 50
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PageTreatment, any>({
        path: `/treatments`,
        method: "GET",
        query: query,
        ...params,
      }),
  };
  treatmentsByDate = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name GetTreatmentsByDateRange
     * @request GET:/treatmentsByDate
     */
    getTreatmentsByDateRange: (
      query: {
        /** @format date-time */
        startDate: string;
        /** @format date-time */
        endDate: string;
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 500
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PageTreatment, any>({
        path: `/treatmentsByDate`,
        method: "GET",
        query: query,
        ...params,
      }),
  };
  test = {
    /**
     * No description
     *
     * @tags patient-controller
     * @name HelloWorld
     * @request GET:/test
     */
    helloWorld: (params: RequestParams = {}) =>
      this.request<Patient[], any>({
        path: `/test`,
        method: "GET",
        ...params,
      }),
  };
  search = {
    /**
     * No description
     *
     * @tags patient-controller
     * @name SearchPatients
     * @request GET:/search
     */
    searchPatients: (
      query: {
        name: string;
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 50
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PagePatient, any>({
        path: `/search`,
        method: "GET",
        query: query,
        ...params,
      }),
  };
  panchkarma = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name GetPanchkarmaByPatientId
     * @request GET:/panchkarma
     */
    getPanchkarmaByPatientId: (
      query: {
        /** @format int64 */
        patientId: number;
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 50
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PagePatientPanchkarma, any>({
        path: `/panchkarma`,
        method: "GET",
        query: query,
        ...params,
      }),
  };
  panchkarmaByDate = {
    /**
     * No description
     *
     * @tags treatment-controller
     * @name GetPanchkarmaByDateRange
     * @request GET:/panchkarmaByDate
     */
    getPanchkarmaByDateRange: (
      query: {
        /** @format date-time */
        startDate: string;
        /** @format date-time */
        endDate: string;
        /**
         * @format int32
         * @default 0
         */
        offset?: number;
        /**
         * @format int32
         * @default 500
         */
        limit?: number;
      },
      params: RequestParams = {},
    ) =>
      this.request<PagePatientPanchkarma, any>({
        path: `/panchkarmaByDate`,
        method: "GET",
        query: query,
        ...params,
      }),
  };
  drugs = {
    /**
     * No description
     *
     * @tags drug-controller
     * @name GetAllDrugs
     * @request GET:/drugs
     */
    getAllDrugs: (params: RequestParams = {}) =>
      this.request<Drug[], any>({
        path: `/drugs`,
        method: "GET",
        ...params,
      }),

    /**
     * No description
     *
     * @tags drug-controller
     * @name SearchDrugs
     * @request GET:/drugs/search
     */
    searchDrugs: (
      query: {
        name: string;
      },
      params: RequestParams = {},
    ) =>
      this.request<Drug[], any>({
        path: `/drugs/search`,
        method: "GET",
        query: query,
        ...params,
      }),

    /**
     * No description
     *
     * @tags drug-controller
     * @name GetActiveDrugs
     * @request GET:/drugs/active
     */
    getActiveDrugs: (params: RequestParams = {}) =>
      this.request<Drug[], any>({
        path: `/drugs/active`,
        method: "GET",
        ...params,
      }),
  };
}
