VERSION 5.00
Begin VB.Form frmShowMess 
   Caption         =   "Message on "
   ClientHeight    =   5700
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4560
   LinkTopic       =   "Form13"
   ScaleHeight     =   5700
   ScaleWidth      =   4560
   StartUpPosition =   3  'Windows Default
   Begin VB.ListBox listWords 
      Height          =   255
      Left            =   2280
      TabIndex        =   12
      Top             =   2640
      Width           =   1815
   End
   Begin VB.ListBox listFrom 
      Height          =   255
      Left            =   840
      TabIndex        =   11
      Top             =   2160
      Width           =   3255
   End
   Begin VB.ListBox listISP 
      Height          =   255
      Left            =   1560
      TabIndex        =   10
      Top             =   1320
      Width           =   1455
   End
   Begin VB.ListBox listWarNum 
      Height          =   255
      Left            =   1920
      TabIndex        =   9
      Top             =   840
      Width           =   1095
   End
   Begin VB.CommandButton cmdOK 
      Caption         =   "OK"
      Height          =   375
      Left            =   3120
      TabIndex        =   8
      Top             =   5160
      Width           =   1215
   End
   Begin VB.Frame frameText 
      Caption         =   "Message Text"
      Height          =   1575
      Left            =   120
      TabIndex        =   6
      Top             =   3240
      Width           =   4215
      Begin VB.Label lblText 
         BackColor       =   &H00FFFFFF&
         Height          =   975
         Left            =   240
         TabIndex        =   7
         Top             =   360
         Width           =   3735
      End
   End
   Begin VB.Label lbl4 
      Caption         =   "Contains the red-flag words"
      Height          =   255
      Left            =   120
      TabIndex        =   5
      Top             =   2640
      Width           =   2175
   End
   Begin VB.Label lblSubject 
      Caption         =   "Subject:"
      Height          =   255
      Left            =   120
      TabIndex        =   4
      Top             =   480
      Width           =   4335
   End
   Begin VB.Label lblSentFrom 
      Caption         =   "Sent from John Nogood (SSN 123-45-6789) to recipients"
      Height          =   255
      Left            =   120
      TabIndex        =   3
      Top             =   1800
      Width           =   4215
   End
   Begin VB.Label lbl2 
      Caption         =   "Recorded at ISPs "
      Height          =   375
      Left            =   120
      TabIndex        =   2
      Top             =   1320
      Width           =   1935
   End
   Begin VB.Label lbl1 
      Caption         =   "Recorded with warrants"
      Height          =   375
      Left            =   120
      TabIndex        =   1
      Top             =   840
      Width           =   2415
   End
   Begin VB.Label lblMessage 
      Caption         =   "Messge sent "
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   12
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      Height          =   615
      Left            =   120
      TabIndex        =   0
      Top             =   0
      Width           =   2895
   End
End
Attribute VB_Name = "frmShowMess"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Public iMessageNumber As Integer
Public strGoBack As String
Public Sub SetMessage(iMessage As Integer)
    Dim dbPredator As Database
    Dim rs As Recordset
    Dim rs2 As Recordset
    Dim strQuery As String
    
    iMessageNumber = iMessage
    
    Set dbPredator = OpenDatabase(dbFile)
    
    '*** Reset fields ***
    frmShowMess.Caption = ""
    lblMessage.Caption = ""
    lblSubject.Caption = ""
    lblText.Caption = ""
    listWarNum.Clear
    listISP.Clear
    listFrom.Clear
    listWords.Clear
    
    '*** get the date, subject, and text ***
    strQuery = "SELECT subject, sdate, msg_text FROM message " & _
                "WHERE message.id = " & iMessageNumber & ";"
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    If Not rs.EOF Then
        frmShowMess.Caption = "Message Sent on " & (Trim(rs.Fields(1)))
        lblMessage.Caption = "Message Sent " & (Trim(rs.Fields(1)))
        lblSubject.Caption = "Subject: " & (Trim(rs.Fields(0)))
        lblText.Caption = (Trim(rs.Fields(2)))
    End If


    '*** Get the red flag words ***
    strQuery = "SELECT rfwords.word FROM msg_rfwords, rfwords " & _
                "WHERE rfwords.id = msg_rfwords.rfword " & _
                "AND msg_rfwords.msg = " & iMessageNumber & ";"
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listWords.AddItem (rs.Fields(0))
        rs.MoveNext
    Wend


    '*** Get from person ***
    
    strQuery = "SELECT person.fname, person.lname, message.sdate FROM message, addresses, person " & _
                    "WHERE message.id = " & iMessageNumber & _
                    "AND message.from_address = addresses.address " & _
                    "AND addresses.person = person.id"

        Set rs = dbPredator.OpenRecordset(strQuery)
        
        If Not rs.EOF Then
            lblSentFrom.Caption = "Sent from " & Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (SSN " & Trim(rs.Fields(2)) & ") to recipients"
        Else
            strQuery = "SELECT message.from_address, message.sdate FROM message " & _
                        "WHERE message.id = " & iMessageNumber

            Set rs = dbPredator.OpenRecordset(strQuery)
            lblSentFrom.Caption = "Sent from (" & Trim(rs.Fields(0)) & ") to recipients"
        End If


    
    
    
    
'*******************OLD FNAME CODE **********************
'    strQuery = "SELECT person.fname, person.lname, person.ssn FROM person, addresses, message " & _
'                "WHERE addresses.person = person.id " & _
'                "AND message.id = " & iMessageNumber & " " & _
'                "AND message.from_address = addresses.address;"
                
'    Set rs = dbPredator.OpenRecordset(strQuery)
    
'    If (Not rs.EOF) Then
'        lblSentFrom.Caption = "Sent from " & Trim(rs.Fields(0)) & " " & Trim(rs.Fields(1)) & " (SSN " & Trim(rs.Fields(2)) & ") to recipients"
'    End If
'********************END OLD FNAME CODE *************

    '*** Get warrants ***
    strQuery = "SELECT msg_warrants.warrant FROM msg_warrants " & _
                "WHERE msg_warrants.msg = " & iMessageNumber & ";"
                
                
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        listWarNum.AddItem (rs.Fields(0))
        rs.MoveNext
    Wend
    
    
    
    '*** Get recipients ***
    
    strQuery = "SELECT msg_to.address FROM msg_to " & _
                "WHERE msg_to.msg = " & iMessageNumber & ";"
    
    Set rs = dbPredator.OpenRecordset(strQuery)
    
    While (Not rs.EOF)
        strQuery = "SELECT person.fname, person.lname, person.ssn FROM person, addresses " & _
                    "WHERE person.id = addresses.person " & _
                    "AND addresses.address = '" & Trim(rs.Fields(0)) & "';"
        Set rs2 = dbPredator.OpenRecordset(strQuery)
        
        If Not rs2.EOF Then
            listFrom.AddItem (Trim(rs2.Fields(0))) & " " & (Trim(rs2.Fields(1))) & " (SSN " & (Trim(rs2.Fields(2))) & ")" & "(" & (Trim(rs.Fields(0))) & ")"
        Else
           listFrom.AddItem "(" & (Trim(rs.Fields(0))) & ")"
        End If
        
        rs.MoveNext
    Wend

    listISP.AddItem "AOL"

    dbPredator.Close
End Sub

Private Sub cmdOK_Click()
    
    If strGoBack = "War" Then
        frmWarAct.Visible = True
    End If

    If strGoBack = "RF" Then
        frmRFAct.Visible = True
    End If

    frmShowMess.Visible = False


End Sub

Private Sub Form_Load()
'List1.AddItem ("12345")
'List1.AddItem ("67890")
'List2.AddItem ("AOL")
'List2.AddItem ("a")
'List3.AddItem ("Sue Badkid (SSN 123-45-6789)")
'List4.AddItem ("launder")
'List4.AddItem ("FBS")

End Sub

Private Sub Form_Unload(Cancel As Integer)
    frmShowMess.Visible = False
    frmWarAct.Visible = True
End Sub
