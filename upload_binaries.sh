#!/bin/bash

if [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
if [ "$TRAVIS_BRANCH" == "master" ]; then

  # define some variables
  GH_USER=skiwi2
  GH_REPO=TCG

  # get info about all releases
  echo -e "Getting info about previous releases"
  curl -X GET -H "Authorization: token ${GH_TOKEN}" \
       "https://api.github.com/repos/${GH_USER}/${GH_REPO}/releases" > json.txt

  # extract info only about only "latest-release" tag
  cat json.txt |jq 'map(select (.tag_name == "latest-master"))' > latest.txt

  # get id of release
  ID_TO_DELETE=`cat latest.txt |jq '.[0].id'`

  # default to 1 if nothing was found
  if [ "$ID_TO_DELETE" == "" -o "$ID_TO_DELETE" == "null" ]; then
    echo -e "Defaulting to release id 1"
    ID_TO_DELETE=1
  fi

  # delete previous release
  echo -e "Deleting release number ${ID_TO_DELETE}\n"
  curl -X DELETE -H "Authorization: token ${GH_TOKEN}" \
     "https://api.github.com/repos/${GH_USER}/${GH_REPO}/releases/${ID_TO_DELETE}"

  # delete previous tag
  echo -e "Deleting latest-master tag\n"
  curl -X DELETE -H "Authorization: token ${GH_TOKEN}" \
    "https://api.github.com/repos/${GH_USER}/${GH_REPO}/git/refs/tags/latest-master"


  echo -e "Creating a release\n"
  curl -X POST -H "Authorization: token ${GH_TOKEN}" \
      -d '{"tag_name": "latest-master", "target_commitish": "master", "name": "master-'${TRAVIS_BUILD_NUMBER}'", "body": "Automatic release based on latest commit to master branch generated by Travis CI.", "draft": false, "prerelease": true}' "https://api.github.com/repos/${GH_USER}/${GH_REPO}/releases" > json.txt
  IDDI=`cat json.txt | jq '.id'`
  echo -e "Release identifier ${IDDI}"

  # build JAR file
  mvn package

  # print all files in target directory
  for filename in /home/travis/build/${GH_USER}/${GH_REPO}/target/*
  do
    if [ -f "$filename" ]; then
      actualsize=$(wc -c "$filename" | cut -f 1 -d ' ')
      echo "${filename} (${actualsize} bytes)"
    else
      echo "${filename}"
    fi
  done;

  echo -e "Uploading JAR\n"
  curl -X POST -H "Authorization: token ${GH_TOKEN}" \
     -H "Accept: application/vnd.github.manifold-preview" \
     -H "Content-Type: application/zip" \
     --data-binary @/home/travis/build/${GH_USER}/${GH_REPO}/target/TCG-1.0-SNAPSHOT.jar \
     "https://uploads.github.com/repos/${GH_USER}/${GH_REPO}/releases/${IDDI}/assets?name=tcg-master-${TRAVIS_BUILD_NUMBER}.jar"

  echo -e "Done uploading\n"

  echo -e "Force draft status false on release {$IDDI}\n"
  curl -X POST -H "Authorization: token ${GH_TOKEN}" \
      -d '{"draft": false}' "https://api.github.com/repos/${GH_USER}/${GH_REPO}/releases/${IDDI}" > json_force.txt
  echo -e "Done\n"
fi
fi