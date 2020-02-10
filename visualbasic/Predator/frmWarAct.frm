VERSION 5.00
Begin VB.Form frmWarAct 
   Caption         =   "WarrantActivity"
   ClientHeight    =   3195
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   7875
   LinkTopic       =   "Form1"
   ScaleHeight     =   3195
   ScaleWidth      =   7875
   StartUpPosition =   3  'Windows Default
   Begin VB.ListBox listMess 
      Height          =   1425
      Left            =   240
      TabIndex        =   2
      Top             =   840
      Width           =   7215
   End
   Begin VB.CommandButton cmdDisplay 
      Caption         =   "Display Message"
      Height          =   495
      Left            =   240
      TabIndex        =   1
      Top             =   2520
      Width           =   1575
   End
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   495
      Left            =   2160
      TabIndex        =   0
      Top             =   2520
      Width           =   1215
   End
   Begin VB.Label lblAnnounce 
      Caption         =   "Warrant has intercepted the following messages:"
      Height          =   495
      Left            =   240
      TabIndex        =   3
      Top             =   120
      Width           =   4215
   End
End
Attribute VB_Name = "frmWarAct"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public iWarrantNumber As Integer
Dim iMessageMap(256) As String

Private Sub cmdDisplay_Click()
    frmShowMess.SetMessage (iMessageMap(listMess.ListIndex))
    
    If frmShowMess.iMessageNumber > -1 Then
        frmShowMess.strGoBack = "War"
        frmShowMess.Visible = True
        frmWarAct.Visible = False
    Else
        MsgBox "The message that you selected was not available"
    End If
    
End Sub

Private Sub cmdOK_Click()
    frmWarAct.Visible = False
    frmDataAnal.Visible = True
End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmWarAct.Visible = False
    frmDataAnal.Visible = True

End Sub

Public Sub setWarrant(iWarrant As Integer)
    Dim strQuery As String
    Dim rs As Recordset
    Dim rs2 As Recordset
    Dim r23 As Recordset
    Dim dbPredator As Database
    Dim i As Integer
    Dim strTemp As String
    
    Set dbPredator = OpenDatabase(dbFile)
    
    iWarrantNumber = iWarrant
        
    strQuery = "SELECT id FROM warrants " & _
                "WHERE id = " & iWarrantNumber & "; "
    Set rs = dbPredator.OpenRecordset(strQuery)
    If rs.EOF Then
        iWarrantNumber = -1
    End If
    
    
            
    '*** Reset All Fields ***
    listMess.Clear
    
    '*** Insert the warrant number into the box ***
    lblAnnounce.Caption = "Warrant " & iWarrantNumber & " has intercepted the following messages"
    
    
    i = 0
    
    While i < 256
        iMessageMap(i) = -1
        i = i + 1
    Wend
    
    
    
    If iWarrantNumber > -1 Then
        '*** Set sender information ***
        strQuery = "SELECT person.fname, person.lname, sdate, message.id FROM msg_warrants, message, person, addresses " & _
                    "WHERE msg_warrants.warrant = " & iWarrantNumber & " " & _
                    "AND message.id = msg_warrants.msg " & _
                    "AND message.from_address = addresses.address " & _
                    "AND addresses.person = person.id"
        Set rs = dbPredator.OpenRecordset(strQuery)

        i = 0
        While (Not rs.EOF)
    
            strTemp = (Trim(rs.Fields(0))) & " " & (Trim(rs.Fields(1))) & " to "
            
            '*** Set recipient information ***
            strQuery = "SELECT msg_to.address FROM msg_to " & _
                        "WHERE msg_to.msg = " & (Trim(rs.Fields(3))) & ";"
    
            Set rs2 = dbPredator.OpenRecordset(strQuery)
    
            While (Not rs2.EOF)
                strQuery = "SELECT person.fname, person.lname, person.ssn FROM person, addresses " & _
                        "WHERE person.id = addresses.person " & _
                        "AND addresses.address = '" & Trim(rs.Fields(0)) & "';"
                Set rs3 = dbPredator.OpenRecordset(strQuery)
        
                If Not rs3.EOF Then
                    strTemp = strTemp & " " & (Trim(rs3.Fields(0))) & " " & (Trim(rs3.Fields(1))) & " (SSN " & (Trim(rs3.Fields(2))) & ")" & "(" & (Trim(rs3.Fields(0))) & ")"
                Else
                    strTemp = strTemp & " (" & (Trim(rs2.Fields(0))) & ")"
                End If
        
            rs2.MoveNext
        Wend

            
            strTemp = strTemp & " " & Trim(rs.Fields(2))
            listMess.AddItem strTemp
                        
            
            iMessageMap(i) = (Trim(rs.Fields(3)))
            i = i + 1
            
            rs.MoveNext
        Wend

    End If
    
    dbPredator.Close


End Sub

