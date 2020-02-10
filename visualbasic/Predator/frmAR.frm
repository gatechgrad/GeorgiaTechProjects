VERSION 5.00
Begin VB.Form frmAR 
   Caption         =   "Association Rules"
   ClientHeight    =   4470
   ClientLeft      =   60
   ClientTop       =   345
   ClientWidth     =   4485
   LinkTopic       =   "Form11"
   Picture         =   "frmAR.frx":0000
   ScaleHeight     =   4470
   ScaleWidth      =   4485
   StartUpPosition =   3  'Windows Default
   Begin VB.CommandButton cmdDone 
      Caption         =   "Done"
      Height          =   375
      Left            =   3480
      TabIndex        =   11
      Top             =   2280
      Width           =   855
   End
   Begin VB.CommandButton cmdStop 
      Caption         =   "Stop"
      Height          =   375
      Left            =   3480
      TabIndex        =   10
      Top             =   1800
      Width           =   855
   End
   Begin VB.CommandButton cmdFind 
      Caption         =   "Find"
      Height          =   375
      Left            =   3480
      TabIndex        =   9
      Top             =   1320
      Width           =   855
   End
   Begin VB.ListBox listResults 
      Height          =   840
      Left            =   240
      TabIndex        =   7
      Top             =   2760
      Width           =   4095
   End
   Begin VB.TextBox txtSup 
      Height          =   285
      Left            =   1680
      TabIndex        =   6
      Text            =   "50"
      Top             =   1800
      Width           =   735
   End
   Begin VB.TextBox txtConf 
      Height          =   285
      Left            =   2040
      TabIndex        =   5
      Text            =   "50"
      Top             =   1320
      Width           =   735
   End
   Begin VB.TextBox txtFewer 
      Height          =   285
      Left            =   1200
      TabIndex        =   2
      Text            =   "4"
      Top             =   840
      Width           =   735
   End
   Begin VB.Label lblNum 
      BackStyle       =   0  'Transparent
      Caption         =   "Found 4 rules."
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0080FFFF&
      Height          =   375
      Left            =   240
      TabIndex        =   8
      Top             =   3720
      Width           =   1575
   End
   Begin VB.Label lbl4 
      BackStyle       =   0  'Transparent
      Caption         =   "Support at least                %"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0080FFFF&
      Height          =   495
      Left            =   240
      TabIndex        =   4
      Top             =   1800
      Width           =   3015
   End
   Begin VB.Label lbl3 
      BackStyle       =   0  'Transparent
      Caption         =   "Confidence at least                %"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0080FFFF&
      Height          =   495
      Left            =   240
      TabIndex        =   3
      Top             =   1320
      Width           =   3015
   End
   Begin VB.Label lbl2 
      BackStyle       =   0  'Transparent
      Caption         =   "Fewer than             Red-Flag Words on the left side"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0080FFFF&
      Height          =   495
      Left            =   240
      TabIndex        =   1
      Top             =   840
      Width           =   3855
   End
   Begin VB.Label lbl1 
      BackStyle       =   0  'Transparent
      Caption         =   "Find all association rules with:"
      BeginProperty Font 
         Name            =   "MS Sans Serif"
         Size            =   8.25
         Charset         =   0
         Weight          =   700
         Underline       =   0   'False
         Italic          =   0   'False
         Strikethrough   =   0   'False
      EndProperty
      ForeColor       =   &H0080FFFF&
      Height          =   495
      Left            =   240
      TabIndex        =   0
      Top             =   240
      Width           =   3015
   End
End
Attribute VB_Name = "frmAR"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Private Sub cmdDone_Click()
    frmAR.Visible = False
    frmDataAnal.Visible = True
End Sub


Private Sub Form_Unload(Cancel As Integer)
    frmAR.Visible = False
    frmDataAnal.Visible = True

End Sub
