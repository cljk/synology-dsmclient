# SYNO.Office.*

## File listings

### get recycle bin
```
mode:"self"
filter:{"recycle":true}
sort_by:"mtime"
sort_direction:"DESC"
field:{"link_id":false,"shortcut":true,"tag":true,"ctime":true,"recycle":true,"acl":true}
api:SYNO.Office.Node
method:list
version:1
```

### own files
```
mode:"self"
filter:{"parent_id":"root","recycle":false}
sort_by:"mtime"
sort_direction:"DESC"
field:{"link_id":false,"shortcut":true,"tag":true,"ctime":true,"recycle":true,"acl":true}
api:SYNO.Office.Node
method:list
version:1
```

### shared with me
```
mode:"other"
sort_by:"mtime"
sort_direction:"DESC"
field:{"link_id":false,"shortcut":true,"tag":true,"ctime":true,"recycle":true,"acl":true}
api:SYNO.Office.Node
method:list
version:1
```

### shared by me
```
mode:"self"
filter:{"sharing":true}
sort_by:"mtime"
sort_direction:"DESC"
field:{"link_id":false,"shortcut":true,"tag":true,"ctime":true,"recycle":true,"acl":true}
api:SYNO.Office.Node
method:list
version:1
```

### recent
```
mode:"recent"
filter:{"recycle":false}
field:{"atime":true,"shortcut":true,"tag":true}
limit:1000
sort_by:"atime"
api:SYNO.Office.Node
method:list
version:1
```

### starred
```
sort_by:"mtime"
sort_direction:"DESC"
field:{"shortcut":true,"tag":true,"ctime":true,"recycle":true}
api:SYNO.Office.Shortcut
method:list
version:1
```




### shared with me - response
```JSON
{
  "data": {
    "nodes": [
      {
        "acl": {
          "dsm_group": {
            "65539": {
              "name": "GROUP-Name",
              "perm": "rw"
            }
          },
          "enabled": true
        },
        "brief": "",
        "category": "node",
        "ctime": 1500883166,
        "encrypt": false,
        "locale": "",
        "mtime": 1500883166,
        "ntype": "dir",
        "object_id": "1033_88CFD0CF3F0751EE23DD5FF3A76CDD06D1E47A50447850DEB3988256C2CE145C1ECC58F53A42970C43F529BAE49CC3CB",
        "owner": {
          "display_name": "create-user",
          "uid": 1033
        },
        "parent_id": "1033_#00000000",
        "path": [],
        "perm": "rw",
        "recycle": false,
        "shortcut": false,
        "tag": null,
        "thumb": null,
        "title": "Foldername",
        "ver": "3644f075c3b949bec3ea67508ec1c179f267d34c"
      },
 
      {
        "acl": {
          "dsm_group": {
            "": {
              "name": "root",
              "perm": "ro"
            }
          },
          "enabled": true
        },
        "brief": "",
        "category": "node",
        "ctime": 1500551320,
        "encrypt": false,
        "locale": "",
        "mtime": 1500551384,
        "ntype": "doc",
        "object_id": "1034_7D4BC0AE4108F387AED6B6EA20EB10CB3EA1F9BF7FFAEBD6F2036C45A8AB746D1D7BDFDA55F7FB3A69EA6EF837995EEF",
        "owner": {
          "display_name": "create-user",
          "uid": 1034
        },
        "parent_id": "1034_89F853B768C3ABE6B6F944A4930FED783F0BDCE8C8BC956B4477DB78305A1E07FA2426E9A2883917DBC03E47170C94C0",
        "path": [],
        "perm": "ro",
        "recycle": false,
        "shortcut": false,
        "style": null,
        "tag": null,
        "thumb": null,
        "title": "Testdokument 1",
        "ver": "c8512c1266f539cb46a2e5769992184100d8df25"
      }
    ],
    "offset": 0,
    "total": 4
  },
  "success": true
}
```


### get folder files
```
filter:{"parent_id":"1033_88CFD0CF3F0751EE23DD5FF3A76CDD06D1E47A50447850DEB3988256C2CE145C1ECC58F53A42970C43F529BAE49CC3CB"}
mode:"other"
sort_by:"mtime"
sort_direction:"DESC"
field:{"link_id":false,"shortcut":true,"tag":true,"ctime":true,"recycle":true,"acl":true}
api:SYNO.Office.Node
method:list
version:1
```

### get folder files - response
```JSON
{
  "data": {
    "nodes": [
      {
        "acl": {
          "dsm_group": {
            "65539": {
              "name": "GROUP-Name",
              "perm": "rw"
            }
          },
          "enabled": true
        },
        "brief": "",
        "category": "node",
        "ctime": 1500885232,
        "encrypt": false,
        "locale": "",
        "mtime": 1500900649,
        "ntype": "doc",
        "object_id": "1033_DCF8F4183BB90EC26B7CE2D2DDF93FD464CA4D18DF25C73C73EC566E805F2DB5EE05B74418431834D7DD9A39BE81ACE5",
        "owner": {
          "display_name": "create-user",
          "uid": 1033
        },
        "parent_id": "1033_88CFD0CF3F0751EE23DD5FF3A76CDD06D1E47A50447850DEB3988256C2CE145C1ECC58F53A42970C43F529BAE49CC3CB",
        "path": [],
        "perm": "rw",
        "recycle": false,
        "shortcut": false,
        "style": null,
        "tag": null,
        "thumb": null,
        "title": "Document-Name",
        "ver": "ffb4b84bcb42c4abac181eaf7223884c18abc861"
      }
    ],
    "offset": 0,
    "total": 1
  },
  "success": true
}
```