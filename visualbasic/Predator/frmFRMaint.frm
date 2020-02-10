VERSION 5.00
Begin VB.Form frmRFMaint 
   Caption         =   "Maintain Red-Flag Words"
   ClientHeight    =   3195
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   5040
   LinkTopic       =   "Form10"
   ScaleHeight     =   3195
   ScaleWidth      =   5040
   StartUpPosition =   3  'Windows Default
   Begin VB.TextBox txtRedFlagWord 
      Height          =   285
      Left            =   3360
      TabIndex        =   4
      Top             =   1320
      Width           =   1455
   End
   Begin VB.CommandButton cmdDone 
      Caption         =   "Done"
      Height          =   495
      Left            =   1920
      TabIndex        =   3
      Top             =   1920
      Width           =   1215
   End
   Begin VB.CommandButton cmdAdd 
      Caption         =   "Add"
      Height          =   495
      Left            =   1920
      TabIndex        =   2
      Top             =   1200
      Width           =   1215
   End
   Begin VB.CommandButton cmdDel 
      Caption         =   "Delete"
      Height          =   495
      Left            =   1920
      TabIndex        =   1
      Top             =   480
      Width           =   1215
   End
   Begin VB.ListBox listWords 
      Height          =   2400
      Left            =   240
      TabIndex        =   0
      Top             =   360
      Width           =   1335
   End
End
Attribute VB_Name = "frmRFMaint"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Dim dbPredator As Database
Private Sub cmdAdd_Click()
    addWord
End Sub

Private Sub cmdDel_Click()
    Dim strRemoveWord As String
    Dim deleteQuery As QueryDef
    Dim strQuery As String
    
    strRemoveWord = listWords.List(listWords.ListIndex)
    
    strQuery = "DELETE FROM rfwords " & _
                "WHERE word = '" & strRemoveWord & "'"
    Set deleteQuery = dbPredator.CreateQueryDef("", strQuery)
    deleteQuery.Execute
    
    displayWords
End Sub

Private Sub cmdDone_Click()
    frmRFMaint.Visible = False
    frmDataMan.Visible = True
End Sub

Private Sub Form_Load()
  Set dbPredator = OpenDatabase(dbFile)
  displayWords
End Sub

Private Sub Form_Unload(Cancel As Integer)
    dbPredator.Close
    frmRFMaint.Visible = False
    frmDataMan.Visible = True
End Sub
Private Sub displayWords()
    Dim strQuery As String
    Dim rs As Recordset

    listWords.Clear
    strQuery = "SELECT word FROM rfwords"
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listWords.AddItem Trim(rs.Fields(0))
        rs.MoveNext
    Wend

End Sub
Private Sub addWord()
    Dim updateQuery As QueryDef
    Dim rs As Recordset
    
    Dim strQuery As String
    Dim iIndex As Integer
    Dim temp As Field
    
    strQuery = "SELECT MAX(id) FROM rfwords"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
      
      If IsNull(rs.Fields(0)) Then
        iIndex = 1
      Else
        iIndex = rs.Fields(0)
        iIndex = iIndex + 1
      End If

    
    If Not txtRedFlagWord = "" Then
      strQuery = "INSERT INTO rfwords(id, word)" & _
      "VALUES (" & iIndex & ", '" & txtRedFlagWord.text & "')"
    
      Set updateQuery = dbPredator.CreateQueryDef("", strQuery)
      updateQuery.Execute
    End If
    
    txtRedFlagWord = ""
    displayWords


End Sub
Private Sub txtRedFlagWord_KeyPress(KeyAscii As Integer)
    If KeyAscii = 13 Then
    addWord
    End If
End Sub
