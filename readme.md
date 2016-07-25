# University Recommendation API

# Overview

The College Entrance Examination is a standardized test  which is used for college admissions in China. It's similar as the SAT in the US but unlike SAT, it's the only criterial for college admissions. The examination contents vary from province to province, and the grade report includes scores for each subject and the rank of the total score in the province. After all the students received their grades, they are divided into three batches based on their total scores. Each student can apply for 6 universities and 6 majors for each university, but the application starting date and applicable universities are different for each batch of the students.

The goal of the API is to provide accessible endpoints for students to get recommendations based on their score or score rank. And also easily to update the enrollment data each year.

# End Points Quick Look

 `/recommendations` supports GET
 + Get a list of university information based on score or rank

 `/batch-scores` supports GET and POST
 + GET and POST batch score records

 `/batch-scores/{id}` supports PUT and DELETE
 + PUT and DELETE batch score record

# Sample

## GET

Get a list of recommendations based on a rank of a student's score

```json

> GET api/v0/recommendations?by=ranking&student_type=science&&province=fujian&&batch=1&lower_limit=0&upper_limit=1000&language=en

[
  {
    "id": 1,
    "university": {
      "name": "Tsinghua University",
      "is985": true,
      "is211": true
    },
    "major": null,
    "year": 2013,
    "studentCount": 63,
    "scoreAvg": 664,
    "rank": 61,
    "scoreBatch": 501,
    "scoreDiff": 163
  },
  {
    "id": 2,
    "university": {
      "name": "Tsinghua University",
      "is985": true,
      "is211": true
    },
    "major": null,
    "year": 2014,
    "studentCount": 0,
    "scoreAvg": 677,
    "rank": 54,
    "scoreBatch": 506,
    "scoreDiff": 171
  },
  ...
]
```

## POST

Sample request:

```
curl /api/v0/batch-scores \
    --insecure \
    --key ***.cer \
    --user "username:password" \
    --header "Content-Type: Application/json" \
    --data '{"id": null, "studentPool": { "province": "湖北", "studentType": "文科", "batch": 2}, "year": 2015, "minScore": 400}'
```

Sample response:

```json
{
  "id": 48,
  "studentPool": {
    "province": "湖北",
    "studentType": "文科",
    "batch": 2
  },
  "year": 2015,
  "minScore": 400
}
```
