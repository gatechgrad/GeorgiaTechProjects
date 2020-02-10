VERSION 5.00
Begin VB.Form Form2 
   Caption         =   "Predator Upload"
   ClientHeight    =   4560
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   5745
   LinkTopic       =   "Form2"
   Picture         =   "pred.frx":0000
   ScaleHeight     =   4560
   ScaleWidth      =   5745
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton Command2 
      Caption         =   "Cancel"
      Height          =   495
      Left            =   4560
      TabIndex        =   7
      Top             =   3480
      Width           =   975
   End
   Begin VB.CommandButton Command1 
      Caption         =   "OK"
      Height          =   495
      Left            =   4560
      TabIndex        =   6
      Top             =   2760
      Width           =   975
   End
   Begin VB.ComboBox Combo1 
      Height          =   315
      Left            =   3480
      TabIndex        =   4
      Text            =   "Combo1"
      Top             =   720
      Width           =   1575
   End
   Begin VB.FileListBox File1 
      Height          =   1065
      Left            =   120
      TabIndex        =   2
      Top             =   3120
      Width           =   2655
   End
   Begin VB.DriveListBox Drive1 
      Height          =   315
      Left            =   120
      TabIndex        =   1
      Top             =   840
      Width           =   2655
   End
   Begin VB.DirListBox Dir1 
      Height          =   1665
      Left            =   120
      TabIndex        =   0
      Top             =   1320
      Width           =   2655
   End
   Begin VB.Label Label1 
      BackColor       =   &H80000005&
      BackStyle       =   0  'Transparent
      Caption         =   "Choose the file containing the e-mail log"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00C0FFFF&
      Height          =   615
      Index           =   1
      Left            =   120
      TabIndex        =   5
      Top             =   240
      Width           =   2895
   End
   Begin VB.Label Label1 
      BackColor       =   &H80000005&
      BackStyle       =   0  'Transparent
      Caption         =   "Choose the ISP"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   9.75
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H00C0FFFF&
      Height          =   255
      Index           =   0
      Left            =   3480
      TabIndex        =   3
      Top             =   360
      Width           =   1815
   End
End
Attribute VB_Name = "Form2"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False

Dim DriveName As String

Private Sub Command1_Click()
MsgBox "422 messages uploaded. 97 were red flag messages, and 12 were covered by a warrant.", vbExclamation, "Predator upload complete"


End Sub

Private Sub Dir1_Change()
File1.Path = Dir1.Path
End Sub

Private Sub Drive1_Change()
On Error GoTo poo
Dir1.Path = Drive1.Drive
DriveName = Drive1.Drive

GoTo done

poo:
    MsgBox "Sorry, device not available"
    Drive1.Drive = DriveName
done:
End Sub

Private Sub Form_Load()
DriveName = "c:\"
End Sub

