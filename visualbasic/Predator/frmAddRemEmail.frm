VERSION 5.00
Begin VB.Form frmAddRemEmail 
   Caption         =   "Add/Remove Mail Addresses"
   ClientHeight    =   5205
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4680
   LinkTopic       =   "Form7"
   ScaleHeight     =   5205
   ScaleWidth      =   4680
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdDone 
      Caption         =   "Done"
      Height          =   495
      Left            =   120
      TabIndex        =   8
      Top             =   4680
      Width           =   1575
   End
   Begin VB.TextBox txtNewAddress 
      Height          =   285
      Left            =   1920
      TabIndex        =   7
      Text            =   "Text1"
      Top             =   4080
      Width           =   2415
   End
   Begin VB.CommandButton cmdAdd 
      Caption         =   "Add Address"
      Height          =   495
      Left            =   120
      TabIndex        =   6
      Top             =   3960
      Width           =   1575
   End
   Begin VB.CommandButton cmdRemove 
      Caption         =   "Remove Address"
      Height          =   495
      Left            =   120
      TabIndex        =   5
      Top             =   3240
      Width           =   1575
   End
   Begin VB.ListBox listAddresses 
      Height          =   645
      Left            =   120
      TabIndex        =   4
      Top             =   2400
      Width           =   4215
   End
   Begin VB.ListBox listPeopleListed 
      Height          =   645
      Left            =   120
      TabIndex        =   2
      Top             =   1080
      Width           =   4215
   End
   Begin VB.Label lbl2 
      Caption         =   "The selected person has the following e-mail addresses:"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   2040
      Width           =   4455
   End
   Begin VB.Label lbl1 
      Caption         =   "The following persons are listed on this warrant:"
      Height          =   375
      Left            =   120
      TabIndex        =   1
      Top             =   720
      Width           =   3615
   End
   Begin VB.Label lblWarNum 
      Caption         =   "Warrant 1234567"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   18
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   615
      Left            =   120
      TabIndex        =   0
      Top             =   120
      Width           =   3375
   End
End
Attribute VB_Name = "frmAddRemEmail"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Const MAX_LIST_SIZE As Integer = 256
Public iWarrantNumber As Integer
Dim iPeopleMap(MAX_LIST_SIZE) As Integer
Public Sub setWarrant(i As Integer)
    Dim dbPredator As Database
    
    Set dbPredator = OpenDatabase("Predator.mdb")
    
    iWarrantNumber = i
    
    '*** Reset All Fields ***
    listPeopleListed.Clear
    listAddresses.Clear
    txtNewAddress.text = ""
    
    '*** Insert the warrant number into the box ***
    strQuery = "SELECT * FROM warrants " & _
                "WHERE id = " & iWarrantNumber & ";"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    If Not rs.EOF Then
        createPeopleList
    Else
        iWarrantNumber = -1
    End If


End Sub
Private Sub createPeopleList()
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
    Dim i As Integer
    
    '*** Reset the people map ***
    i = 0
    While i < MAX_LIST_SIZE
        iPeopleMap(i) = -1
        i = i + 1
    Wend
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT person, fname, lname, SSN FROM valid_from, person " & _
                "WHERE warrant = " & iWarrantNumber & " " & _
                "AND person = person.id " & _
                "UNION " & _
                "SELECT person, fname, lname, SSN FROM valid_to, person " & _
                "WHERE warrant = " & iWarrantNumber & " " & _
                "AND person = person.id " & _
                "UNION " & _
                "SELECT person_1, fname, lname, SSN FROM valid_between, person " & _
                "WHERE warrant = " & iWarrantNumber & " " & _
                "AND person_1 = person.id " & _
                "UNION " & _
                "SELECT person_2, fname, lname, SSN FROM valid_between, person " & _
                "WHERE warrant = " & iWarrantNumber & " " & _
                "AND person_2 = person.id " & _
                ";"


    Set rs = dbPredator.OpenRecordset(strQuery)
    
    
    i = 0
    While (Not rs.EOF)
        
        'listPeopleListed.AddItem "Hello World"
        listPeopleListed.AddItem Trim(rs.Fields(1)) & " " & Trim(rs.Fields(2)) & " (SSN " & Trim(rs.Fields(3)) & ")"
        iPeopleMap(i) = Trim(rs.Fields(0))
        i = i + 1
        
        rs.MoveNext
    Wend
    
    
    
    dbPredator.Close
End Sub

Private Sub cmdAdd_Click()
    addAddress
End Sub

Private Sub cmdDone_Click()
    frmAddRemEmail.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub cmdRemove_Click()
    removeAddress
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmAddRemEmail.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub displayAddresses()
    Dim dbPredator As Database
    Dim strQuery As String
    Dim rs As Recordset
    Dim i As Integer
    
    Set dbPredator = OpenDatabase(dbFile)
    
    strQuery = "SELECT address FROM addresses " & _
                "WHERE person = " & iPeopleMap(listPeopleListed.ListIndex) & ";"

    Set rs = dbPredator.OpenRecordset(strQuery)
    
    listAddresses.Clear
    
    While (Not rs.EOF)
        listAddresses.AddItem Trim(rs.Fields(0))
               
        rs.MoveNext
    Wend
    
    
    
    dbPredator.Close
    
    
End Sub

Private Sub addAddress()
    Dim updateQuery As QueryDef
    Dim strQuery As String
    
    Set dbPredator = OpenDatabase(dbFile)
    
    If Not txtNewAddress = "" And listPeopleListed.ListIndex > -1 Then
      strQuery = "INSERT INTO addresses(person, address)" & _
      "VALUES (" & iPeopleMap(listPeopleListed.ListIndex) & ", '" & txtNewAddress.text & "')"
    
      Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
      updateQuery.Execute
      displayAddresses
      txtNewAddress = ""
    Else
        MsgBox ("An error occured when trying to add the e-mail address")
    End If
    
    
    dbPredator.Close
    
    


End Sub
Private Sub removeAddress()
    Dim updateQuery As QueryDef
    Dim strQuery As String
    
    Set dbPredator = OpenDatabase(dbFile)
    
    If listAddresses.ListIndex > -1 Then
      strQuery = "DELETE FROM addresses " & _
      "WHERE person = " & iPeopleMap(listPeopleListed.ListIndex) & " " & _
      "AND address = '" & listAddresses.List(listAddresses.ListIndex) & "';"
    
      Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
      updateQuery.Execute
      displayAddresses
      txtNewAddress = ""
    Else
        MsgBox ("No address selected to remove")
    End If
    
    
    dbPredator.Close
    
    


End Sub


Private Sub listPeopleListed_Click()
    displayAddresses
End Sub

Private Sub txtNewAddress_KeyPress(KeyAscii As Integer)
    If KeyAscii = 13 Then
        addAddress
    End If
End Sub
